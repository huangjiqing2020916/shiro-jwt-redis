package com.shiro.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiro.jwt.entity.RealmUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author huangjiqing
 * @date 2020/11/17 10:04
 */
@Mapper
public interface RealmUserMapper extends BaseMapper<RealmUser> {

    RealmUser selectUserByUsername(@Param("username") String username);

}
