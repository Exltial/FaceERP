package com.ncut.face.erp.bootstrap.auth;

import com.ncut.face.erp.service.user.UserService;
import com.ncut.face.erp.service.user.domain.UserInfoModel;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class FaceRealm extends AuthorizingRealm {
    @Resource
    UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取用户pin
        String faceId = (String) principalCollection.getPrimaryPrincipal();
        UserInfoModel user = userService.getInfoByFaceId(faceId);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(user.getUserRole());
        simpleAuthorizationInfo.addStringPermission(user.getUserRole());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String faceId = authenticationToken.getPrincipal().toString();
        UserInfoModel user = userService.getInfoByFaceId(faceId);
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            return new SimpleAuthenticationInfo(user.getFaceId(), user.getPassword(), user.getUserName());
        }
    }
}
