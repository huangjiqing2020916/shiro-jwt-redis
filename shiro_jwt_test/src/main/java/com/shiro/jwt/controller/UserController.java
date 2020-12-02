package com.shiro.jwt.controller;

import com.shiro.jwt.exeception.RRException;
import com.shiro.jwt.util.JWTTokenUtil;
import com.shiro.jwt.util.R;
import com.shiro.jwt.util.UniversalExpression;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huangjiqing
 * @date 2020/11/25 10:31
 */
@RestController
@RequestMapping("user")
public class UserController {

    //    @RequiresPermissions("user:add")
    @RequestMapping("/add")
    public R add(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("add", "add").put("username", username);
    }

    //    @RequiresPermissions("user:update")
    @RequestMapping("/update")
    public R update(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("update", "update").put("username", username);
    }

}
