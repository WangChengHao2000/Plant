package com.web.flower.mapper;

import com.web.flower.bean.User;

public interface UserMapper {

    void insertUser(User user);

    User selectUser(User user);

    String getNickNameByAccount(int account);
}
