package com.shiro.jwt.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.jwt.entity.RealmUser;
import com.shiro.jwt.mapper.RealmUserMapper;
import com.shiro.jwt.service.RealmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:07
 */
@Service("RealmUserService")
public class RealmUserServiceImpl extends ServiceImpl<RealmUserMapper, RealmUser> implements RealmUserService {

    @Autowired
    private RealmUserMapper realmUserDao;

    @Override
    public RealmUser queryUserByUsername(String username) {
        return realmUserDao.selectUserByUsername(username);
    }
}
