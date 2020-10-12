package com.cav.clairvoyance.service;


import org.springframework.web.multipart.MultipartFile;


public interface FileService {


    public String singleUpload(MultipartFile file, String uploadPath) throws Exception;

}
