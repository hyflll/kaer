package com.example.kaer.controller;

import com.example.kaer.entity.User;
import com.example.kaer.service.IUserService;
import com.example.kaer.util.jsonResult;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {
    //操作成功状态码
    private static final int ok = 200;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
//   登入
    @RequestMapping("/login")
    public jsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);
//        全局session
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        return new jsonResult<User>(ok,data);
    }

    //发送邮箱验证
    @GetMapping("/sendEmail/{email}")
    public jsonResult<Void> sendEmailCode(@PathVariable String email){
        String code = (String)redisTemplate.opsForValue().get(email);
        if(!StringUtils.isEmpty(code)){
            return new jsonResult<>(ok);
        }
        code = userService.getCode();
        userService.sendEmail(email,code);
        redisTemplate.opsForValue().set(email,code,1,TimeUnit.MINUTES);
        return new jsonResult<>(ok);
    }

    //注册
    @RequestMapping("/reg")
    public jsonResult<Void> reg(User user,HttpSession session) {
        userService.reg(user);
        return new jsonResult<>(ok);
    }

}
