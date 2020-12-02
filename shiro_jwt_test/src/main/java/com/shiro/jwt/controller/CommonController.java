package com.shiro.jwt.controller;

import com.shiro.jwt.util.R;
import com.shiro.jwt.util.UniversalExpression;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommonController {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public R handleShiroException(Exception ex) {
        return R.error("您没有权限访问此页面");//您没有权限访问此页面
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public R authorizationException(Exception ex) {
        return R.error("请先登录");//请先登录
    }

    @RequestMapping("/common/noauth")
    public R noauth() {
        return R.error("您没有权限访问此页面");//您没有权限访问此页面
    }

    @RequestMapping("/common/noLogin")
    public R noLogin() {
        return R.error("请先登录");//未登录
    }

    @RequestMapping("/common/success")
    public R success() {
        return R.ok("登录成功");
    }
}


