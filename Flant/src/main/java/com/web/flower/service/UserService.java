package com.web.flower.service;

import com.web.flower.bean.User;

public interface UserService {

    User register(User user);

    User login(User user);

    String getNickNameByAccount(int account);
}
