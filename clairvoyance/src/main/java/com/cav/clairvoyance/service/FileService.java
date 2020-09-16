package com.cav.clairvoyance.service;


import com.cav.clairvoyance.domain.Result;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public Result upload(MultipartFile file) throws Exception;
}
