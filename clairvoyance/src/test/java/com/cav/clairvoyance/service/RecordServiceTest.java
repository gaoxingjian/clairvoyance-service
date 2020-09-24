package com.cav.clairvoyance.service;

import com.cav.clairvoyance.domain.Record;
import com.cav.clairvoyance.mapper.RecordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordServiceTest {

    @Autowired
    RecordService recordService;
    @Test
    public void queryById() {
        Record record = recordService.queryById(1L);
        System.out.println("record = "+record.toString());
    }

    @Test
    public void queryByHash() {

        String hash = "A7FCFC6B5269BDCCE571798D618EA219A68B96CB87A0E21080C2E758D23E4CE9";
        System.out.println("record = "+recordService.queryByHash(hash));
    }

    @Test
    public void saveRecord() {
        Record record =  new Record();
        record.setHash("A7FCFC6B5269BDCCE571798D618EA219A68B96CB87A0E21080C2E758D23E4CE9");
        record.setDetectResult("No problem.");
        record.setCreateTime(new Date());
        System.out.println(recordService.saveRecord(record));
    }

    @Test
    public void deleteById() {
    }
}