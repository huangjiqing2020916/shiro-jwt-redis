package com.shiro.jwt.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.jwt.entity.TPermission;
import com.shiro.jwt.entity.TRole;
import com.shiro.jwt.entity.TUser;
import com.shiro.jwt.mapper.SysMapper;
import com.shiro.jwt.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:07
 */
@Service("SysService")
public class SysServiceImpl extends ServiceImpl<SysMapper, TUser> implements SysService {

    @Autowired
    private SysMapper realmUserDao;

    @Override
    public TUser queryUserByUsername(String username) {
        return realmUserDao.selectUserByUsername(username);
    }

    @Override
    public List<TRole> queryRoleByUser(Long userId) {
        return realmUserDao.selectRoleByUser(userId);
    }

    @Override
    public List<TPermission> queryPermissionByRole(Long roleId) {
        return realmUserDao.selectPermissionByRole(roleId);
    }
}
