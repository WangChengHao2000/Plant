package com.web.flower.mapper;

import com.web.flower.bean.Record;

import java.util.List;

public interface RecordMapper {

    void insertRecord(Record record);

    List<Record> selectRecordByAccount(int account);

    List<Record> selectAllRecord();

}
