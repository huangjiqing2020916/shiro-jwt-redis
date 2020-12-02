package com.shiro.jwt.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shiro.jwt.entity.TPermission;
import com.shiro.jwt.entity.TRole;
import com.shiro.jwt.entity.TUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:06
 */
public interface SysService extends IService<TUser> {
    TUser queryUserByUsername(String username);

    List<TRole> queryRoleByUser (Long userId);

    List<TPermission> queryPermissionByRole(Long roleId);

}
