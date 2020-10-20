package com.cav.clairvoyance.controller;


import com.cav.clairvoyance.domain.Record;
import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.service.AnalyseService;
import com.cav.clairvoyance.service.FileService;
import com.cav.clairvoyance.service.RecordService;
import com.cav.clairvoyance.utils.DataResult;
import com.cav.clairvoyance.utils.FileUtil;
import com.cav.clairvoyance.utils.SHA1;
import com.cav.clairvoyance.utils.UUIDUtil;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "检测")
@RestController
public class AnalyseController {


    @Autowired
    private AnalyseService analyseService;
    @Autowired
    private FileService fileService;
    @Autowired
    private RecordService recordService;

    @Value("${file.overwrite}")
    private boolean overwrite;

    @Value("${file.delete}")
    private boolean delete;

    @PostMapping("/analyse")
    public DataResult analyse(@RequestBody String data) throws IOException, InterruptedException {
        DataResult result = DataResult.success();
        // SHA1求Hash
        String hash = SHA1.getSHA1String(data);
        // System.out.println("Hash:" + hash);
        String detectResult;
        // 从数据库中搜索Hash
        List<Record> recordList = recordService.queryByHash(hash);
        if (!recordList.isEmpty()) {
            String resultId = recordList.get(0).getDetectResult();
            if (overwrite) {
                fileService.deleteDetectionResultById(resultId);
                fileService.deleteContractById(resultId);
                recordService.deleteById(recordList.get(0).getId());
            } else {
                detectResult = fileService.getDetectionResultById(resultId);
                result.setData(detectResult);
                return result;
            }
        }

        // 把用户输入的代码保存到.sol文件中
        String uuid = UUIDUtil.getUUIDEndWithTime();
        String filePath = fileService.saveContract(data, uuid);
        // 检测
        detectResult = analyseService.analyseFile(filePath);
        // System.out.println(detectResult);
        fileService.saveDetectionResult(detectResult, uuid);
        // 将结果存入数据库
        Record record = new Record();
        record.setSrcCode(uuid);
        record.setHash(hash);
        record.setDetectResult(uuid);
        record.setCreateTime(new Date());
        recordService.saveRecord(record);

        result.setData(detectResult);
        return result;
    }

    @PostMapping("/batchAnalyse/{id}")
    public DataResult batchAnalyse(@PathVariable("id") String id) throws IOException, InterruptedException {
        if (id == null || id.isEmpty()) throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        String tempDir = fileService.getTempDir();
        String dirPath = tempDir + File.separator + UUIDUtil.getTimeFromUUID(id) + File.separator + id;
        String reportsPath = dirPath + File.separator + "detection_reports_" + id;

        FileUtil.deleteFile(reportsPath);
        FileUtil.makeDir(reportsPath);

        File file = new File(dirPath);
        ArrayList<File> solList = new ArrayList<File>();
        // 获取所有.sol文件
        FileUtil.getFilesBySuffix(file, fileService.getContractExt(), solList);
        if (solList.isEmpty())
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        String hash;
        String uuid = null;
        String detectResult = null;
        String resultId;
        List<Record> recordList;
        for (File solFile : solList) {
            hash = SHA1.getSHA1String(solFile);
            // 从数据库中搜索Hash
            recordList = recordService.queryByHash(hash);

            if (!recordList.isEmpty()) {
                resultId = recordList.get(0).getDetectResult();
                if (overwrite || delete) {
                    fileService.deleteDetectionResultById(resultId);
                    fileService.deleteContractById(resultId);
                    recordService.deleteById(recordList.get(0).getId());
                } else {
                    detectResult = fileService.getDetectionResultById(resultId);
                    uuid = resultId;
                }
            }
            if (delete) continue;
            if (recordList.isEmpty() || overwrite) {
                detectResult = analyseService.analyseFile(solFile.getAbsolutePath());
                uuid = UUIDUtil.getUUIDEndWithTime();
                // 存入仓库
                fileService.saveContract(FileUtil.readFile(solFile), uuid);
                fileService.saveDetectionResult(detectResult, uuid);
                // 存入数据库
                Record record = new Record();
                record.setSrcCode(uuid);
                record.setHash(hash);
                record.setDetectResult(uuid);
                record.setCreateTime(new Date());
                recordService.saveRecord(record);
            }
            // 将检测结果写入temp
            FileUtil.writeStringToFile(detectResult, reportsPath + File.separator + solFile.getName() + "_report_" + uuid + "." + fileService.getReportExt());
        }
        return DataResult.success(id);
    }
}
