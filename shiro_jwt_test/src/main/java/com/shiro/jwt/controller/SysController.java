package com.shiro.jwt.controller;

import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.exeception.RRException;
import com.shiro.jwt.service.RealmUserService;
import com.shiro.jwt.util.*;
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
    private RealmUserService rService;

    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/login")
    public String login(@RequestBody RealmUser realmUser) {
        String token = "";
        String username = realmUser.getUsername();
        String password = realmUser.getPassword();
        RealmUser user = rService.queryUserByUsername(username);

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
        return token;
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        redisUtil.del(UniversalExpression.Key.REDISKEY.getValue() + token);
        return R.ok();
    }
}
