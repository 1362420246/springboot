package com.qbk.dao.one;

import java.util.List;
import java.util.Map;

import com.qbk.bean.UserBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/***
 * 用户管理数据曾
 */
@Repository(value = "userDao")
public interface UserDao {


	/**
	 * @Description: 查询用户列表
	 */
	public List<UserBean> getUserList(Map<String, Object> paramsMap);

	public UserBean getUser(UserBean userBean);

	public void updateUser(UserBean userBean);

	public void delete(int userId);

	public void addUser(UserBean userBean);
}
