package com.cav.clairvoyance.utils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class UUIDUtilTest {

    @Test
    public void getUUIDEndWithTime() {
        System.out.println(UUIDUtil.getUUIDEndWithTime());
    }

    @Test
    public void getTimeFromUUID() {
        System.out.println(UUIDUtil.getTimeFromUUID("123"));
    }

}