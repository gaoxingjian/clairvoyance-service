package com.cav.clairvoyance.utils;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FileUtilTest {

    @Test
    public void makeDir() {
    }

    @Test
    public void deleteFile() {
        FileUtil.deleteFile("/Users/jason/develop/clairvoyance-service/files/7E374FE833DF4A0C8EA662CEE68C6A83/test1.zip");
    }

    @Test
    public void getFilesBySuffix() {
        File file = new File("/Users/jason/develop/clairvoyance-service/files/0210FF6165474ACE95D255C1241D3ED2");
        ArrayList<File> list = new ArrayList<File>();
        FileUtil.getFilesBySuffix(file,".go",list);
        System.out.println(list);
    }
}