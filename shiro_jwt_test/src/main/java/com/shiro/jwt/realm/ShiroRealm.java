package com.shiro.jwt.realm;

import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.service.RealmUserService;
import com.shiro.jwt.util.JWTTokenUtil;
import com.shiro.jwt.util.JwtToken;
import com.shiro.jwt.util.RedisUtil;
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

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //验证权限时
        RealmUser sysUser = null;
        String username = null;
        if (principals != null) {
            sysUser = (RealmUser) principals.getPrimaryPrincipal();
            username = sysUser.getUsername();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(sysUser.getPerms());

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        //验证token时
        String token = (String) auth.getCredentials();
//        }
        // 校验token有效性
        RealmUser loginUser = this.checkUserTokenIsEffect(token);
        if (loginUser == null) {
            new AuthenticationException("用户不存在");
        }
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }

    /**
     * 校验token的有效性
     *
     * @param token
     */
    public RealmUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        String existToken = String.valueOf(redisUtil.get("PREFIX_USER_TOKEN_" + token));
        if (existToken != null && !"".equals(existToken) && !"null".equals(existToken)) {
            int minute = 2;
            redisUtil.expire("PREFIX_USER_TOKEN_" + token, minute);
            String username = JWTTokenUtil.getUsername(token);

            // 查询用户信息
            RealmUser sysUser = userService.queryUserByUsername(username);
            if (sysUser == null) {
                log.error("用户不存在");
                throw new AuthenticationException("用户不存在");
            }
            return sysUser;
        } else {
            log.error("用户不存在");
            throw new AuthenticationException();
        }
    }

}
