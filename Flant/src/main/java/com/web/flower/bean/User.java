package com.web.flower.bean;

public class User {

    private int account;
    private String nickName;
    private String password;

    public User() {
    }

    public User(int account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    public User(int account, String nickName, String password) {
        this.account = account;
        this.nickName = nickName;
        this.password = password;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}