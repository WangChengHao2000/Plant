package com.web.flower.service.impl;

import com.web.flower.bean.Record;
import com.web.flower.mapper.RecordMapper;
import com.web.flower.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired(required = false)
    private RecordMapper recordMapper;

    @Override
    public Record insertRecord(Record record) {
        recordMapper.insertRecord(record);
        return record;
    }

    @Override
    public List<Record> selectRecordByAccount(int account) {
        return recordMapper.selectRecordByAccount(account);
    }

    @Override
    public List<Record> selectRecordByAccount() {
        return recordMapper.selectAllRecord();
    }

}
