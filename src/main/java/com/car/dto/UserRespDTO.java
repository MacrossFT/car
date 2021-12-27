package com.car.dto;

import java.util.List;

public class UserRespDTO {

    /**
     * 用户名
     */
    private String name;

    /**
     * 权限集合
     */
    private List<String> roles;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}