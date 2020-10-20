package com.cav.clairvoyance.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FileService {


    public String getTempDir();

    public String getContractDir();

    public String getReportDir();

    public String getContractExt();

    public String getReportExt();

    public String singleUpload(MultipartFile file) throws Exception;

    public String getContractById(String uuid) throws IOException;

    public String getDetectionResultById(String uuid) throws IOException;

    public String saveContract(String contract, String uuid) throws IOException;

    public String saveDetectionResult(String result, String uuid) throws IOException;

    public void deleteContractById(String uuid);

    public void deleteDetectionResultById(String uuid);
}
