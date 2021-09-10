package com.web.flower.service;

import com.web.flower.bean.Record;

import java.util.List;

public interface RecordService {

    Record insertRecord(Record record);

    List<Record> selectRecordByAccount(int account);

    List<Record> selectRecordByAccount();
}
