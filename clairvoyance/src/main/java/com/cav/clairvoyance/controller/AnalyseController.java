package com.cav.clairvoyance.controller;


import com.cav.clairvoyance.domain.Record;
import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.service.AnalyseService;
import com.cav.clairvoyance.service.RecordService;
import com.cav.clairvoyance.utils.DataResult;
import com.cav.clairvoyance.utils.FileUtil;
import com.cav.clairvoyance.utils.SHA1;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(tags = "检测")
@RestController
public class AnalyseController {

    static final Logger logger = LoggerFactory.getLogger(AnalyseController.class);

    @Autowired
    private AnalyseService analyseService;
    @Autowired
    private RecordService recordService;

    @PostMapping("/analyse")
    public DataResult analyse(@RequestBody String data) throws IOException, InterruptedException {
        DataResult result = DataResult.success();
        // SHA1求Hash
        String hash = SHA1.getSHA1String(data);
        System.out.println("Hash:" + hash);

        // 从数据库中搜索Hash
        List<Record> recordList = recordService.queryByHash(hash);
        if (!recordList.isEmpty()) {
            result.setData(recordList.get(0).getDetectResult());
            return result;
        }
        // 把用户输入的代码保存到.sol文件中
        String uuid = UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
        String dirPath = new File("files").getAbsolutePath();
        File dir = new File(dirPath, uuid);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = dir.getAbsolutePath() + File.separator + uuid + ".sol";
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
        out.write(data);
        out.close();

        // 检测
        String detectResult = analyseService.analyseFile(filePath);
        // System.out.println(detectResult);
        // 将结果存入数据库
        Record record = new Record();
        record.setHash(hash);
        record.setDetectResult(detectResult);
        record.setCreateTime(new Date());
        recordService.saveRecord(record);

        result.setData(detectResult);
        return result;

    }

    @PostMapping("/batchAnalyse/{id}")
    public DataResult batchAnalyse(@PathVariable("id") String id) throws IOException, InterruptedException {
        if(id.isEmpty() || id ==null) throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        String dirPath = new File("files").getAbsolutePath();
        String specialDir = dirPath + File.separator + id;
        // 删除mac快照文件
        FileUtil.deleteFile(specialDir+File.separator+"__MACOSX");
        String reportsPath =  specialDir + File.separator + "detection_report_" + id;
        FileUtil.deleteFile(reportsPath);
        FileUtil.makeDir(reportsPath);
        File file = new File(dirPath, id);
        ArrayList<File> solList = new ArrayList<File>();
        // 获取所有.sol文件
        FileUtil.getFilesBySuffix(file, ".sol", solList);
        if (solList.isEmpty())
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        String hash;
        String uuid;
        String detectResult;
        BufferedWriter writer;
        List<Record> recordList;
        for (File solFile : solList) {
            hash = SHA1.getSHA1String(solFile);
            // 从数据库中搜索Hash
            recordList = recordService.queryByHash(hash);

            if (recordList.isEmpty()) {
                detectResult = analyseService.analyseFile(solFile.getAbsolutePath());
                logger.info(detectResult);
                // 将结果存入数据库
                Record record = new Record();
                record.setHash(hash);
                record.setDetectResult(detectResult);
                record.setCreateTime(new Date());
                recordService.saveRecord(record);
            } else {
                detectResult = recordList.get(0).getDetectResult();
            }
            // 将检测结果写入文件
            uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
            FileUtil.writeStringToFile(detectResult,reportsPath + File.separator + solFile.getName() + "_report_" + uuid + new SimpleDateFormat("YYYYMMddHHmmss").format(new Date()) + ".txt");
        }
        return DataResult.success(id);
    }
}
