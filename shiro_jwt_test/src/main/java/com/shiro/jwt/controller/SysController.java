package com.shiro.jwt.controller;

import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.exeception.RRException;
import com.shiro.jwt.service.RealmUserService;
import com.shiro.jwt.util.JWTTokenUtil;
import com.shiro.jwt.util.PasswordUtil;
import com.shiro.jwt.util.R;
import com.shiro.jwt.util.RedisUtil;
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

    private static final String SALT = "RCGTeGiH";

    @Autowired
    private RealmUserService rService;

    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/login")
    public R login(@RequestBody RealmUser realmUser) {
        String token = "";
        String username = realmUser.getUsername();
        String password = realmUser.getPassword();
        RealmUser user = rService.queryUserByUsername(username);

        if (user == null) {
            return R.error("用户名不存在");
        } else {
            String saltPassword = PasswordUtil.encrypt(username, password, SALT);
            if (!saltPassword.equals(user.getPassword())) {
                return R.error("密码错误");
            } else {
                token = JWTTokenUtil.createJwtToken(user.getUsername(), user.getPassword());
                redisUtil.set("PREFIX_USER_TOKEN_" + token, token, JWTTokenUtil.REDIS_TIME);//time=秒
                System.err.println(token);
                System.err.println("PREFIX_USER_TOKEN_" + token);
            }
        }
        return R.ok().put("username", username).put("token", token);
    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisUtil.del("PREFIX_USER_TOKEN_" + token);
        return R.ok();
    }
}
