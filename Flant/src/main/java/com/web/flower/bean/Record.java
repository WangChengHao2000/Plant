package com.web.flower.bean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Record {

    private int recordID;
    private int account;
    private String plantName;
    private Timestamp time;
    private String address;

    public Record() {
    }

    public Record(int account, String plantName, Timestamp time, String address) {
        this.account = account;
        this.plantName = plantName;
        this.time = time;
        this.address = address;
    }

    public Record(int recordID, int account, String plantName, Timestamp time, String address) {
        this.recordID = recordID;
        this.account = account;
        this.plantName = plantName;
        this.time = time;
        this.address = address;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public int getAccount() {
        return account;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setTime(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ENGLISH);
            dateFormat.setLenient(false);
            Date timeDate = dateFormat.parse(time);
            Timestamp result = new Timestamp(timeDate.getTime());
            this.time = result;
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (this.time == null) {
                this.time = new Timestamp(System.currentTimeMillis());
            }
        }
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
