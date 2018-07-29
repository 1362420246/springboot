package com.qbk.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.qbk.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import com.qbk.bean.UserBean;
import com.qbk.service.UserService;

/****
 * 用户管理控制层
 */
@Controller
@RequestMapping(value = "user")
public class UserController {
    //org.slf4j.Logger
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Value("${server.port}")//读取配置文件中的参数
    private String port;

    @Autowired //注入redis
    private RedisTemplate redisTemplate;

    //redis测试
    @ResponseBody
    @RequestMapping("redisTestSet")
    public Map<String, Object> redisTestSet() {
        //测试
        UserBean user = new UserBean();
        user.setUserName("卡卡");
        redisTemplate.opsForValue().set("user:id:01", user);
        redisTemplate.opsForHash().put("service", "port", port);
        //设置有效期 : Hash数据也可设置有效期
        redisTemplate.expire("user:id:01",10, TimeUnit.SECONDS);
        redisTemplate.expire("service",10, TimeUnit.SECONDS);
        return ResultUtils.getResult(true, "redis存储成功");
    }

    //redis测试
    @ResponseBody
    @RequestMapping("redisTestGet")
    public Map<String, Object> redisTestGet() {
        UserBean user = (UserBean) redisTemplate.opsForValue().get("user:id:01");
        String port = (String) redisTemplate.opsForHash().get("service", "port");
        return ResultUtils.getResult(true,port ,user);
    }

    @ResponseBody
    @RequestMapping("getUser")
    public Map<String, Object> getUserById(UserBean userBean) {
        try {
            UserBean user = userService.getUser(userBean);
            return  ResultUtils.getResult(true, "查询用户列表成功",user);
        } catch (Exception e) {
            logger.error("查询错误", e);
            return ResultUtils.getResult(false, "查询用户列表错误");
        }
    }
    @ResponseBody
    @RequestMapping("updateUser")
    public Map<String, Object> updateUser(UserBean userBean) {
        try {
            userService.updateUser(userBean);
            return ResultUtils.getResult(true, "修改成功");
        } catch (Exception e) {
            logger.error("修改错误", e);
            return ResultUtils.getResult(false, "修改错误");
        }
    }
    @ResponseBody
    @RequestMapping("deleteUser")
    public Map<String, Object> deleteUser(int userId) {
        try {
            return userService.deleteUser(userId);
        } catch (Exception e) {
            logger.error("删除错误", e);
            return ResultUtils.getResult(false, "删除错误");
        }
    }
    @ResponseBody
    @RequestMapping("addUser")
    public Map<String, Object> addUser(UserBean userBean) {
        try {
            return userService.addUser(userBean);
        } catch (Exception e) {
            logger.error("添加错误", e);
            return ResultUtils.getResult(false, "添加错误");
        }
    }

    /**
     * @Description: 查询用户列表
     */
    @ResponseBody
    @RequestMapping("getUserList.do")
    public Map<String, Object> getUserList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value = "loginName", required = false) String loginName,
            @RequestParam(value = "userId", required = false) Integer userId,
            String roleIds) {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        try {
            paramsMap.put("pageNum", pageNum);
            paramsMap.put("pageSize", pageSize);
            paramsMap.put("loginName", loginName);
            paramsMap.put("userId", userId);
            paramsMap.put("roleIds", roleIds);
            return userService.getUserList(paramsMap);
        } catch (Exception e) {
            logger.error("查询错误", e);
            return ResultUtils.getResult(false, "查询用户列表错误");
        }
    }

}
