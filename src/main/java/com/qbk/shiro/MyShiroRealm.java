package com.qbk.shiro;

import com.qbk.service.ShiroService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;


/**
 * Realm 是控制认证和授权的核心部分，也是开发人员必须自己实现的部分。
 自定义的Realm通过继承AuthorizingRealm类，实现它的两个方法：doGetAuthorizationInfo和doGetAuthenticationInfo实现自己的认证和授权逻辑，
 其中doGetAuthenticationInfo处理授权逻辑暂时不实现。
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    public ShiroService shiroService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户账号
        String username = token.getPrincipal().toString();

        String password = shiroService.getPasswordByUsername(username);
        if (password != null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    username,   //认证通过后，存放在session,一般存放user对象
                    password,   //用户数据库中的密码
                    getName());    //返回Realm名
            return authenticationInfo;
        }
        return null;
    }
}
