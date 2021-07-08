package com.ataraxia.microservices.dao;

import com.ataraxia.microservices.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author lilong
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * @Return com.ataraxia.microservices.entity.SysUser
     * @Author Li Long
     * @Description 根据用户名查询用户信息
     * @Date 18:01 2021/7/5
     * @Params [username]
     */
    SysUser loadUserByUsername(String username);
}
