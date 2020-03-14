package com.wcj.utils.configuration.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: create by wcj
 * @date: 2020/2/29 0029
 * @time: 下午 14:02
 * @Description: 自定义realm
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 执行授权逻辑
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行授权逻辑");
        //资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        //todo 获取登陆用户
        //Member member = (Member) subject.getPrincipal();
        //添加资源的授权字符串
        //todo 获取用户角色
        //Set<String> memberRoles = memberService.getMemberRoles(member);
        //todo 获取用户权限
        //Set<String> memberPerms = memberService.getMemberPerms(member);
        //todo 设置用户角色
        //info.setRoles(memberRoles);
        //todo 设置用户权限
        //info.setStringPermissions(memberPerms);
        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
       /* Member member = memberService.getMemberByOpenid(usernamePasswordToken.getUsername());
        if (member.getStatus() == 0){
            throw new DisabledAccountException();
        }
        return new SimpleAuthenticationInfo(member, member.getUnionid(), member.getName());*/
        return new SimpleAuthenticationInfo("", "", "");
    }
}
