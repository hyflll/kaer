package com.example.kaer.mapper;

import com.example.kaer.entity.User;

public interface UserMapper {
//    登入
    User findByUsername(String username);
//    注册
    Integer insert(User user);
}
