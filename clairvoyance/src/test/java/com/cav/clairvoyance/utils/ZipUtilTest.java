package com.cav.clairvoyance.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZipUtilTest {

    @Test
    public void zipFiles() {
    }

    @Test
    public void zipDir() {
    }

    @Test
    public void unzip() {
        ZipUtil.unzip("/Users/jason/files/reentrancy.zip","/Users/jason/files");
        // ZipUtil.zipDir("/Users/jason/files/test1.zip","/Users/jason/files/u2pppw");
    }
}