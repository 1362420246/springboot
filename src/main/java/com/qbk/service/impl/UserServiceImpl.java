package com.qbk.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qbk.service.UserService;
import com.qbk.dao.one.UserDao;
import com.qbk.bean.UserBean;
import com.qbk.utils.ResultUtils;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/***
 * 用户管理业务层
 */
@Service(value = "userService")
//@Transactional( value = "txManager1",rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override  //添加缓存 。缓存的是方法的结果
    //value：缓存名称(配置文件中) condition：触发条件，只有满足条件的情况才会加入缓存，默认为空，既表示全部都加入缓存
    //key：缓存的key，默认为空，既表示使用方法的参数类型及参数值作为key
     // @Cacheable(value = "testCache", key = "#userBean.userId", condition = "#userBean.userId == 1")
//	@Cacheable(value = {"user:id"},key = "#userBean.userId" )
	@Cacheable(value = {"user:id"},key =  "'u'.concat(#userBean.userId.toString())" )//redis 中key为： user:id::u1
    public UserBean getUser(UserBean userBean) {
        UserBean user = userDao.getUser(userBean);
        return user;
    }

    @Override //更新缓存。缓存的是方法的结果
    @CachePut(value = {"user:id"},key =  "'u'.concat(#userBean.userId.toString())" )
    public UserBean updateUser(UserBean userBean) {
        userDao.updateUser(userBean);
        UserBean user = userDao.getUser(userBean);
        return user;
    }

    @Override //删除缓存
    @CacheEvict(value = {"user:id"},key =  "'u'.concat(#userId.toString())" )
    public Map<String, Object> deleteUser(int userId) {
        userDao.delete(userId);
        return ResultUtils.getResult(true, "删除成功");
    }

   /*
    @Transactional ：事务
    value 可选的限定描述符，指定使用的事务管理器
    propagation   事务传播行为设置
    isolation  事务隔离级别设置
    readOnly 知否只读，默认读写
    timeout 事务超时时间设置
    rollbackFor 回滚的异常类数组
    rollbackForClassName  导致事务回滚的异常类名字数组
    noRollbackFor  不会导致事务回滚的异常类数组
    noRollbackForClassName  不会导致事务回滚的异常类名字数组*/
    @Override
//    @Transactional(isolation = Isolation.READ_UNCOMMITTED ,rollbackFor=Exception.class)
//    @Transactional( value = "txManager1",readOnly=true)
    public Map<String, Object> addUser(UserBean userBean) {
        userDao.delete(3);
       // int a = 10/0 ; //两个数据库之间添加异常，看操作一是否回滚
        userDao.addUser(userBean);
        return ResultUtils.getResult(true, "添加成功");
    }

    /**
     * @Description: 查询用户列表   分页插件PageHelper
     */
    @Override
//    @Transactional(readOnly=true)
    public Map<String, Object> getUserList(Map<String, Object> paramsMap)
            throws Exception {
        Integer pageNum = (Integer) paramsMap.get("pageNum");
        Integer pageSize = (Integer) paramsMap.get("pageSize");
//		String loginName = (String) paramsMap.get("loginName");
//		Integer userId = (Integer) paramsMap.get("userId");
        String roleIds = (String) paramsMap.get("roleIds");
        String[] roleIdArry = null;
        if (StringUtils.isNotBlank(roleIds)) {
            roleIdArry = roleIds.split(",");
            paramsMap.put("roleIdArry", roleIdArry);
        }
        if (pageNum != -1) {
            PageHelper.startPage(pageNum, pageSize);
        }
        List<UserBean> list = userDao.getUserList(paramsMap);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(list);
        logger.info("查询成功");
        return ResultUtils.getResult(true, "查询用户列表成功", pageInfo);
    }


}
