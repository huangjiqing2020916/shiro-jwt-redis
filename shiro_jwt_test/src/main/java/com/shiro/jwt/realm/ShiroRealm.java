package com.shiro.jwt.realm;

import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.service.RealmUserService;
import com.shiro.jwt.util.JWTTokenUtil;
import com.shiro.jwt.util.JwtToken;
import com.shiro.jwt.util.RedisUtil;
import com.shiro.jwt.util.UniversalExpression;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * @author huangjiqing
 * @date 2020/11/25 10:10
 */
@Component
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private RealmUserService userService;

    @Autowired
    @Lazy
    private RedisUtil redisUtil;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        System.err.println("认证");
        System.err.println(auth.getCredentials());
        //验证token时
        String token = (String) auth.getCredentials();
//        }
        // 校验token有效性
        RealmUser loginUser = this.checkUserTokenIsEffect(token);
        if (loginUser == null) {
            new AuthenticationException(UniversalExpression.MenuType.USERNOTEXIST.getValue());
        }
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("授权");
        // 校验token有效性
        RealmUser sysUser = this.checkUserTokenIsEffect((String) principals.getPrimaryPrincipal());

        //验证权限时
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        System.err.println(sysUser.getPerms());
        info.addStringPermission(sysUser.getPerms());

        return info;
    }


    /**
     * 校验token的有效性
     *
     * @param token
     */
    public RealmUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        String existToken = String.valueOf(redisUtil.get(UniversalExpression.Key.REDISKEY.getValue() + token));
        if (existToken != null && !"".equals(existToken) && !"null".equals(existToken)) {
            redisUtil.expire(UniversalExpression.Key.REDISKEY.getValue() + token, UniversalExpression.Variable.NEWREDISTIME.getValue());
            String username = JWTTokenUtil.getUsername(token);

            // 查询用户信息
            RealmUser sysUser = userService.queryUserByUsername(username);
            if (sysUser == null) {
                log.error(UniversalExpression.MenuType.USERNOTEXIST.getValue());
                throw new AuthenticationException(UniversalExpression.MenuType.USERNOTEXIST.getValue());
            }
            return sysUser;
        } else {
            log.error(UniversalExpression.MenuType.TOKENERROR.getValue());
            throw new AuthenticationException();
        }
    }

}
