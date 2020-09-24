package com.cav.clairvoyance.service;

import com.cav.clairvoyance.domain.Record;

import java.util.List;

public interface RecordService {

    public Record queryById(Long id);

    public List<Record> queryByHash(String hash);

    public int saveRecord(Record record);

    public int deleteById(Long id);
}
