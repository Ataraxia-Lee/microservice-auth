package com.ataraxia.microservices.service.impl;

import com.ataraxia.microservices.dao.SysRoleMapper;
import com.ataraxia.microservices.dao.SysUserMapper;
import com.ataraxia.microservices.entity.SysUser;
import com.ataraxia.microservices.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MyPC
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userMapper.loadUserByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("NOT FIND USER INFO");
        }
        return sysUser;
    }
}
