package com.cav.clairvoyance.service;

import java.io.IOException;

public interface AnalyseService {

    public String analyseFile(String filePath) throws IOException, InterruptedException;
}
