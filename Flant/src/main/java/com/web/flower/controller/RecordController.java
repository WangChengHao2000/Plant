package com.web.flower.controller;

import com.web.flower.bean.Record;
import com.web.flower.service.RecordService;
import com.web.flower.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class RecordController {

    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/insertRecord", method = RequestMethod.POST)
    public Record insertRecord(@RequestBody Record record) {
        return recordService.insertRecord(record);
    }

    @RequestMapping(value = "/selectRecord", method = RequestMethod.POST)
    public List<Record> selectRecord(@RequestBody int account) {
        return recordService.selectRecordByAccount(account);
    }

    @RequestMapping(value = "/selectAllRecord", method = RequestMethod.POST)
    public List<Record> selectAllRecord() {
        return recordService.selectRecordByAccount();
    }

    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    public String uploadPic(@RequestBody MultipartFile file) {
        String filePath = "/www/project/Plant/picture/plant/";
        String fileName = Math.random() + ".jpg";
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            return "default.jpg";
        }
        return fileName;
    }

    @GetMapping(value = "/downloadPic", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadPic(String filename) throws IOException {
        if (filename.equals("null"))
            filename = "default.jpg";
        File file = new File("/www/project/Plant/picture/plant/" + filename);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

}
