package com.shiro.jwt.util;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * @author huangjiqing
 * @date 2020/11/27 11:41:55
 */
public class UniversalExpression {

    public enum MenuType {
        NOAUTHORITY("您没有权限访问此页面"), NOTLOGGEDIN("请先登录"), USERNOTEXIST("用户不存在"), USERNAMENOTEXIST("用户名不存在"),
        PASSWORDERROR("密码不正确"),TOKENERROR("token异常"),UNKNOWNEXCEPTION("未知异常，请联系管理员");

        private String value;

        MenuType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Key {
        REDISKEY("PREFIX_USER_TOKEN_"), SALT("RCGTeGiH"),TOKEN("token");

        private java.lang.String value;

        Key(java.lang.String value) {
            this.value = value;
        }

        public java.lang.String getValue() {
            return value;
        }
    }

    public enum Variable {
        NEWREDISTIME(5);

        private Integer value;

        Variable(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

}
