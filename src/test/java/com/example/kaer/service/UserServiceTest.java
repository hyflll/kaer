package com.example.kaer.service;

import com.example.kaer.entity.User;
import com.example.kaer.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    private IUserService iUserService;
    @Test
    public void login(){
        User user = iUserService.login("er", "123");
        System.out.println(user);
    }
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("ji");
            user.setPassword("123");
            user.setEmail("2677742124@qq.com");
            user.setCode("738903");
            iUserService.reg(user);
            System.out.println("ok");
        } catch (ServiceException e) {
//获取类的对象再获取类的名称
            System.out.println(e.getClass().getSimpleName());
//获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }


}
