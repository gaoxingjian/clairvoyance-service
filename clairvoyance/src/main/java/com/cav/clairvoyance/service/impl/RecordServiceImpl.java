package com.cav.clairvoyance.service.impl;

import com.cav.clairvoyance.domain.Record;
import com.cav.clairvoyance.mapper.RecordMapper;
import com.cav.clairvoyance.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Record queryById(Long id) {
        return recordMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Record> queryByHash(String hash) {
        Record record = new Record();
        record.setHash(hash);
        return recordMapper.select(record);
    }

    @Transactional
    @Override
    public int saveRecord(Record record) {
        return recordMapper.insertSelective(record);
    }
    @Transactional
    @Override
    public int deleteById(Long id) {
        return recordMapper.deleteByPrimaryKey(id);
    }
}
