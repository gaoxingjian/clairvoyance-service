package com.cav.clairvoyance.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    public JSONObject upload(MultipartFile file) throws Exception;
}
