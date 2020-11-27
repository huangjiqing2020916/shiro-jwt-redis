package com.shiro.jwt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:01
 */
@TableName("realm_user")
@Data
public class RealmUser implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private String perms;
    private String address;

}
