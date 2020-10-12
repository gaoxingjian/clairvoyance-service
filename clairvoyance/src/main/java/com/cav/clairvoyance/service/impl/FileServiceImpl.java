package com.cav.clairvoyance.service.impl;


import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.service.FileService;

import com.cav.clairvoyance.utils.FileUtil;
import com.cav.clairvoyance.utils.ZipUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.UUID;



@Service
public class FileServiceImpl implements FileService {

    public String singleUpload(MultipartFile multipartFile, String uploadPath) throws IOException {
        String originName = multipartFile.getOriginalFilename();
        String extension = FilenameUtils.getExtension(originName);
        if (!extension.equals("zip") && !extension.equals("sol"))
            throw new BusinessException(BaseResponseCode.OPERATION_ERRO);
        String uuid = UUID.randomUUID().toString().replace("-", "") + new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
        File dir = new File(uploadPath, uuid);
        if (!dir.exists()) dir.mkdirs();
        String filepath = dir.getAbsolutePath() + File.separator + originName;
        multipartFile.transferTo(new File(filepath));
        if (extension.equals("zip")) {
            // 解压zip文件
            ZipUtil.unzip(filepath, dir.getAbsolutePath());
            // 删除zip文件
            FileUtil.deleteFile(filepath);
        }
        return uuid;

    }

}
