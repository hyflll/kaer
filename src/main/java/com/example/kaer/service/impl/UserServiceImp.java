package com.example.kaer.service.impl;

import com.example.kaer.entity.User;
import com.example.kaer.mapper.UserMapper;
import com.example.kaer.service.IUserService;
import com.example.kaer.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UserServiceImp implements IUserService {
    @Autowired
    private UserMapper userMapper;
    /**
     * 登入页面
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        String oldPassword = result.getPassword();
        if(result == null){
            throw new UserNotFoundException("用户数据不存在");
        }
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password, salt);
        if(!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        if(result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());
        return user;
    }

    /**
     * 注册
     * @param user
     */
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String code = user.getCode();
        User result = userMapper.findByUsername(username);
        if(result != null){
            throw new UsernameDuplicateException("用户名被占用");
        }
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(code)){
            throw new EmailSendException("邮箱验证异常");
        }
        String mobleCode = redisTemplate.opsForValue().get(email);
        System.out.println(mobleCode);
        if(!code.equals(mobleCode)){
            throw new CodeNotMatchException("验证码匹配异常");
        }
        String oldPassword = user.getPassword();
        String salt= UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        String md5Password = getMD5Password(oldPassword, salt);
        user.setPassword(md5Password);
        user.setIsDelete(0);
        user.setEmail(email);
        System.out.println(user);
        Integer rows = userMapper.insert(user);
        if(rows != 1){
            throw new InsertException("在用户注册过程中产生了未知异常");
        }
    }

    @Override
    public String getCode(){
        int random = (int)(Math.random() * 1000000);
        String code = String.format("%06d", random);
        return code;
    }

    /**
     * 验证码获取
     *
     */
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String UserName;
    @Override
    @Async
    public void sendEmail(String email, String code) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setSubject("卡尔二手车登录验证码");
            mailMessage.setText("尊敬的" + email + "您注册的验证码为:" + code + "有效时间1分钟");
            mailMessage.setTo(email);
            mailMessage.setFrom(UserName);
            // 真正的发送邮件操作，从 from到 to
            mailSender.send(mailMessage);
        }



    /*
     * 定义一个md5算法的加密处理
     * */
    private String getMD5Password(String password,String salt){
        for (int i = 0;i<3;i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
