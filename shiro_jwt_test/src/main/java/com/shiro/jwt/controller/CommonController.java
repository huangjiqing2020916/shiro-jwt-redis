package com.shiro.jwt.controller;

import com.shiro.jwt.util.R;
import com.shiro.jwt.util.UniversalExpression;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class CommonController {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public R handleShiroException(Exception ex) {
        return R.error(UniversalExpression.MenuType.NOAUTHORITY.getValue());
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public R authorizationException(Exception ex) {
        return R.error(UniversalExpression.MenuType.NOTLOGGEDIN.getValue());
    }

    @RequestMapping("/common/noauth")
    public R noauth (){
        return R.error(UniversalExpression.MenuType.NOAUTHORITY.getValue());
    }
}


