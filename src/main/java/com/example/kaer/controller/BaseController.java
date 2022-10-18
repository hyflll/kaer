package com.example.kaer.controller;
import com.example.kaer.controller.ex.EmailSendException;
import com.example.kaer.service.ex.*;
import com.example.kaer.util.jsonResult;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/*
* 控制层类的基类
* */
public class BaseController {
//操作成功状态码
    public static final int ok = 200;

    @ExceptionHandler({ServiceException.class, FileUploadException.class})//用于统一处理抛出异常
    public jsonResult<Void> handleException(Throwable e){
        jsonResult<Void> result = new jsonResult<>(e);
        if(e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        }else if(e instanceof UserNotFoundException){
            result.setState(5001);
            result.setMessage("用户信息未找到");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户的密码错误的异常");
        }else if(e instanceof EmailSendException){
            result.setState(6000);
            result.setMessage("邮箱验证码错误异常");
        }
        return result;
    }

}
