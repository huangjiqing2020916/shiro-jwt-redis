package com.shiro.jwt.controller;

import com.shiro.jwt.entity.TUser;
import com.shiro.jwt.service.SysService;
import com.shiro.jwt.util.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangjiqing
 * @date 2020/11/25 10:34
 */
@RestController
@RequestMapping("sys")
public class SysController {


    @Autowired
    private SysService rService;

    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/login")
    public String login(@RequestBody TUser tUser) {
        String token = "";
        String username = tUser.getUsername();
        String password = tUser.getPassword();
        TUser user = rService.queryUserByUsername(username);

        if (user == null) {
            return UniversalExpression.MenuType.USERNAMENOTEXIST.getValue();
        } else {
            String saltPassword = PasswordUtil.encrypt(username, password, UniversalExpression.Key.SALT.getValue());
            if (!saltPassword.equals(user.getPassword())) {
                return UniversalExpression.MenuType.PASSWORDERROR.getValue();
            } else {
                token = JWTTokenUtil.createJwtToken(user.getUsername(), user.getPassword());
                redisUtil.set(UniversalExpression.Key.REDISKEY.getValue() + token, token, JWTTokenUtil.REDIS_TIME);//time=秒
            }
        }
        SecurityUtils.getSubject().isAuthenticated();
        return token;
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        redisUtil.del(UniversalExpression.Key.REDISKEY.getValue() + token);
        return R.ok();
    }
}
