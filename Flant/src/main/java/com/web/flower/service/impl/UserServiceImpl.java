package com.web.flower.service.impl;

import com.web.flower.bean.User;
import com.web.flower.mapper.UserMapper;
import com.web.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Override
    public User register(User user) {
        userMapper.insertUser(user);
        return user;
    }

    @Override
    public User login(User user) {
        return userMapper.selectUser(user);
    }

    @Override
    public String getNickNameByAccount(int account) {
        return userMapper.getNickNameByAccount(account);
    }

}
