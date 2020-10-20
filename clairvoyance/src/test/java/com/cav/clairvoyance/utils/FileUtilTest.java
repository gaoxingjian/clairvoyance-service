package com.cav.clairvoyance.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
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
        FileUtil.getFilesBySuffix(file, ".go", list);
        System.out.println(list);
    }

    @Test
    public void writeStringToFile() throws IOException {
        String path = "/Users/jason/files/te.sol";
        FileUtil.writeStringToFile("asd\nasd\nerror",path);
    }
    @Test
    public void readFile() throws IOException {
        String path = "/Users/jason/Downloads/detection_report_e3777f34b33e4b32aab1a2aa1ea2bcf820201014172145/test1.sol_report_ef970fa846d5481cb840204294989a3220201014172247.txt";
        System.out.println(FileUtil.readFile(path));
    }
    @Test
    public void getDetectionResultById() throws IOException {

    }
}