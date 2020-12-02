package com.shiro.jwt.controller;

import com.shiro.jwt.util.JWTTokenUtil;
import com.shiro.jwt.util.R;
import com.shiro.jwt.util.UniversalExpression;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

    @RequiresRoles(value = {"role:admin","role:root"},logical = Logical.OR)
    @RequiresPermissions("per:add")
    @RequestMapping("/add")
    public R add(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("add", "add").put("username", username);
    }

    @RequiresRoles(value = {"role:admin"},logical = Logical.OR)
    @RequiresPermissions("per:del")
    @RequestMapping("/del")
    public R del(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("del", "del").put("username", username);
    }

    @RequiresRoles(value = {"role:admin","role:root"},logical = Logical.OR)
    @RequiresPermissions("per:edit")
    @RequestMapping("/edit")
    public R edit(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("edit", "edit").put("username", username);
    }

    @RequiresRoles(value = {"role:admin","role:youke","role:root"},logical = Logical.OR)
    @RequiresPermissions("per:show")
    @RequestMapping("/show")
    public R show(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("show", "show").put("username", username);
    }

    @RequiresRoles(value = {"role:admin"},logical = Logical.OR)
    @RequiresPermissions("per:root")
    @RequestMapping("/root")
    public R root(HttpServletRequest request) {
        String token = request.getHeader(UniversalExpression.Key.TOKEN.getValue());
        String username = JWTTokenUtil.getUsername(token);
        return R.ok().put("root", "root").put("username", username);
    }

}
