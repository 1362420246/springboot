package com.qbk.bean ;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * ClassName: User 
 * @Description: 用户类
 * @author quboka
 * @date 2018年3月22日
 */
public class UserBean implements  Serializable {//

   private static final long serialVersionUID = 9136121254905356626L;

	//用户id
	private Integer userId ;
	//用户名或登录名
	private String loginName ;
	//真实姓名
	private String userName ;
	//用户密码
	private String password ;
	//用户状态0离线，1在线 ,-1删除
	private Integer status ;
	//用户的工作状态   ： 0：空闲中 1：等待中 2：处理中
	private Integer workStatus ;
	//手机号码
	private String mobilePhone ;
	//电子邮箱
	private String email ;
	//注册时间
	private Date createTime ;
	//更新时间
	private Date updatetime ;
	//最后登录时间
	private Date lastLoginTime ;
	//:版本号
	private Integer version ;
	//角色集合
	private List<RoleBean> roleList;


	//归属
	private String affiliation ;
	
	public String getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}
	public List<RoleBean> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<RoleBean> roleList) {
		this.roleList = roleList;
	}
	public Integer getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "UserBean{" +
				"userId=" + userId +
				", loginName='" + loginName + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", status=" + status +
				", workStatus=" + workStatus +
				", mobilePhone='" + mobilePhone + '\'' +
				", email='" + email + '\'' +
				", createTime=" + createTime +
				", updatetime=" + updatetime +
				", lastLoginTime=" + lastLoginTime +
				", version=" + version +
				", roleList=" + roleList +
				", affiliation='" + affiliation + '\'' +
				'}';
	}
}
