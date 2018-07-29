package com.qbk.controller;

import com.qbk.bean.Test;
import com.qbk.result.Result;
import com.qbk.result.ResultGenerator;
import com.qbk.service.TestService;
import com.qbk.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by 13624 on 2018/6/10.
 */
@Slf4j  //可以直接使用 log 变量输出日志
@RestController
@RequestMapping("/api")
public class ApiController {

    //org.slf4j.Logger
    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private TestService testService ;

    //测试不被拦截接口
    @RequestMapping("/getRequest")
    public Map<String, Object> getRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", "zhangsan");
            System.out.println("不存在session");
        } else {
            System.out.println("存在session");
        }
        logger.info("请求成功");
        return ResultUtils.getResult(true, "请求成功");
    }

    //抛异常接口
    @RequestMapping("/error")
    public String error() throws Exception {
        throw new Exception("发生错误");
    }

    //数据源2的接口
    @GetMapping("/getTest")
    public Test getTest()   {
       try{
           Test test = null ;
            test = testService.getTest();
           log.info("test:"+test);
           log.info("日志@Slf4j 注解得使用---------------------------------");
           return test ;
       }catch (Exception e){
           Test test = new Test() ;
           log.error("日志@Slf4j 注解得使用---------------------------------",e);
           return test ;
       }
    }

    @GetMapping("/helloworld")
    public Result helloWorld() {
        return ResultGenerator.genSuccessResult("helloworld");
    }

    @GetMapping("/doLogin")
    public Result doLogin(String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {//AuthenticationException是Shiro封装的异常，如果登录认证没有成功就会抛出这个异常。
            token.clear();
            return ResultGenerator.genFailResult("登录失败，用户名或密码错误！");
        }
        return ResultGenerator.genSuccessResult("登录成功");
    }

}
