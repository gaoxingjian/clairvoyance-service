//package com.cav.clairvoyance.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cav.clairvoyance.service.FileService;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
//
//public class FileServiceImpl implements FileService {
//
//    @Override
//    public JSONObject upload(MultipartFile file) throws Exception {
//        if (file == null || file.isEmpty()) {
//            throw new Exception("未选择需上传的日志文件");
//        }
//        String filePath = new File("clairvoyance").getAbsolutePath();
//        File fileUpload = new File(filePath);
//        if (!fileUpload.exists()) {
//            fileUpload.mkdirs();
//        }
//
//        fileUpload = new File(filePath, file.getOriginalFilename());
//        if (fileUpload.exists()) {
//            throw new Exception("上传的文件已存在");
//        }
//
//        try {
//            file.transferTo(fileUpload);
//
//            return ReturnMessage.success();
//        } catch (IOException e) {
//            throw new GlobalException("上传日志文件到服务器失败：" + e.toString());
//        }
//        return null;
//    }
//}
