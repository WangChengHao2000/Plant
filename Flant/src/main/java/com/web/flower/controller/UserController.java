package com.web.flower.controller;

import com.web.flower.bean.User;
import com.web.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {
        User res = userService.login(user);
        if (res != null)
            return res;
        else
            return user;
    }

    @RequestMapping(value = "/getNickName", method = RequestMethod.GET)
    public String getNickName(int account) {
        return userService.getNickNameByAccount(account);
    }

}
