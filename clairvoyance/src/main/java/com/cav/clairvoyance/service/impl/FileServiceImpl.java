package com.cav.clairvoyance.service.impl;

import com.cav.clairvoyance.domain.Result;
import com.cav.clairvoyance.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class FileServiceImpl implements FileService {

    @Override
    public Result upload(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new Exception("未选择需上传的日志文件");
        }
        String filePath = new File("files").getAbsolutePath();
        System.out.println(filePath);
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath, System.currentTimeMillis()+file.getOriginalFilename());
        if (fileUpload.exists()) {
            throw new Exception("上传的文件已存在");
        }

        try {
            file.transferTo(fileUpload);
            return new Result(200, "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(500, "上传失败");
    }
}
