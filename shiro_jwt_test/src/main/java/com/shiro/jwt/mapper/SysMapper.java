package com.shiro.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.jwt.entity.TPermission;
import com.shiro.jwt.entity.TRole;
import com.shiro.jwt.entity.TUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:04
 */
@Mapper
public interface SysMapper extends BaseMapper<TUser> {

    TUser selectUserByUsername(@Param("username") String username);

    List<TRole> selectRoleByUser (@Param("userId") Long userId);

    List<TPermission> selectPermissionByRole(@Param("roleId") Long roleId);

}
