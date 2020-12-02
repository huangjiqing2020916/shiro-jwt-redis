package com.shiro.jwt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:01
 */
@TableName("t_user")
@Data
public class TUser implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String password;

}
