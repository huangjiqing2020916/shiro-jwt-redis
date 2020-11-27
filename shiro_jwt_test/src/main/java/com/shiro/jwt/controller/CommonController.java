package com.shiro.jwt.controller;

import com.shiro.jwt.util.R;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CommonController {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public R handleShiroException(Exception ex) {
        return R.error("您没有权限访问此页面");
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public R authorizationException(Exception ex) {
        return R.error("请先登录");
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    public String authenticationException(Exception ex) {
        return "token异常";
    }
}


