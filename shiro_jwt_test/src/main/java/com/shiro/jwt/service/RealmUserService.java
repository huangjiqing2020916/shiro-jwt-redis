package com.shiro.jwt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.jwt.entity.RealmUser;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:06
 */
public interface RealmUserService extends IService<RealmUser> {
    RealmUser queryUserByUsername(String username);

}
