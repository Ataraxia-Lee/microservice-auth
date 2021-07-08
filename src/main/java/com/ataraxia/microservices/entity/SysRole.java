package com.ataraxia.microservices.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;

/**
 * @author lilong
 */
public class SysRole implements Serializable {

    private Long id;

    private String roleMark;

    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleMark() {
        return roleMark;
    }

    public void setRoleMark(String roleMark) {
        this.roleMark = roleMark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
