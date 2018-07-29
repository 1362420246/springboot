package com.qbk.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.qbk.bean.UserBean;

/***
 * 用户管理业务层接口
 */
public interface UserService {


	/**
	 * @Description: 查询用户列表
	 */
	public Map<String, Object> getUserList(Map<String, Object> paramsMap)throws Exception;

	public UserBean getUser(UserBean userBean);

	public UserBean updateUser(UserBean userBean);

	public Map<String,Object> deleteUser(int userId);

	public Map<String,Object> addUser(UserBean userBean);
}
