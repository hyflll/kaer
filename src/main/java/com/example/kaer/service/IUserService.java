package com.example.kaer.service;

import com.example.kaer.entity.User;

public interface IUserService {
    //登入
    User login(String username,String password);
    //注册
    void reg(User user);
    //获取code
    String getCode();
    //发送邮箱
    void sendEmail(String email,String code);
}
