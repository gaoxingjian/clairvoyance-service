package com.cav.clairvoyance.service.impl;


import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.service.FileService;

import com.cav.clairvoyance.utils.FileUtil;
import com.cav.clairvoyance.utils.UUIDUtil;
import com.cav.clairvoyance.utils.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;

import java.io.IOException;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static String fileRootDir = null;
    private static String tempDir = null;
    private static String contractDir = null;
    private static String reportDir = null;

    @Value("${file.root.dir.windows}")
    String fileRootDirWindows;

    @Value("${file.root.dir.mac}")
    String fileRootDirMac;

    @Value("${file.root.dir.linux}")
    String fileRootDirLinux;

    // 合约文件扩展名
    @Value("${file.extension.contract}")
    String contractExt;

    // 报告文件扩展名
    @Value("${file.extension.report}")
    String reportExt;

    @Override
    public String getTempDir() {
        return tempDir;
    }

    @Override
    public void clearTempDir() {
        FileUtil.deleteFile(tempDir);
        FileUtil.makeDir(tempDir);
        log.info("Clear temp.");
    }

    @Override
    public String getContractDir() {
        return contractDir;
    }

    @Override
    public String getReportDir() {
        return reportDir;
    }

    @Override
    public String getContractExt() {
        return contractExt;
    }

    @Override
    public String getReportExt() {
        return reportExt;
    }


    @PostConstruct
    public void initFileRepository() {

        // 判断文件夹是否存在，不存在就创建
        String osName = System.getProperty("os.name");

        if (osName.startsWith("Mac OS")) {

            fileRootDir = fileRootDirMac;
        } else if (osName.startsWith("Windows")) {

            fileRootDir = fileRootDirWindows;
        } else {

            fileRootDir = fileRootDirLinux;
        }
        tempDir = fileRootDir + File.separator + "temp";
        contractDir = fileRootDir + File.separator + "contracts";
        reportDir = fileRootDir + File.separator + "detection-reports";
        FileUtil.makeDir(tempDir);
        FileUtil.makeDir(contractDir);
        FileUtil.makeDir(reportDir);
    }

    @Override
    public String singleUpload(MultipartFile multipartFile) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originName);
        if (!extension.equals("zip") && !extension.equals("sol"))
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        String uuid = UUIDUtil.getUUIDEndWithTime();
        String dirPath = tempDir + File.separator + UUIDUtil.getTimeFromUUID(uuid) + File.separator + uuid;
        File dir = new File(dirPath);
        if (!dir.exists()) dir.mkdirs();
        String filepath = dir.getAbsolutePath() + File.separator + originName;
        multipartFile.transferTo(new File(filepath));
        if (extension.equals("zip")) {
            // 解压zip文件
            ZipUtil.unzip(filepath, dir.getAbsolutePath());
            // 删除mac快照文件
            FileUtil.deleteFile(dir.getAbsolutePath() + File.separator + "__MACOSX");
            // 删除zip文件
            FileUtil.deleteFile(filepath);
        }
        return uuid;
    }

    @Override
    public String getContractById(String uuid) throws IOException {
        return FileUtil.getTById(uuid, contractDir, contractExt);
    }

    @Override
    public String getDetectionResultById(String uuid) throws IOException {
        return FileUtil.getTById(uuid, reportDir, reportExt);
    }

    @Override
    public String saveContract(String contract, String uuid) throws IOException {
        return FileUtil.saveT(contract, uuid, contractDir, contractExt);
    }

    @Override
    public String saveDetectionResult(String result, String uuid) throws IOException {
        return FileUtil.saveT(result, uuid, reportDir, reportExt);
    }

    @Override
    public void deleteContractById(String uuid) {
        FileUtil.deleteT(uuid, contractDir, contractExt);
    }

    @Override
    public void deleteDetectionResultById(String uuid) {
        FileUtil.deleteT(uuid, reportDir, reportExt);
    }


}
