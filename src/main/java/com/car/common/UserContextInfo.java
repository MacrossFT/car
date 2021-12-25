package com.car.common;


import com.car.po.UserPO;

/**
 * 用户信息保存
 */
public class UserContextInfo {

    ThreadLocal<UserPO> threadLocal = new ThreadLocal<>();

    private static UserContextInfo userContextInfo = new UserContextInfo();

    public static UserContextInfo getInstance() {
        return userContextInfo;
    }

    public void buildUser(UserPO userPO) {
        threadLocal.set(userPO);
    }

    public UserPO getUser() {
        return threadLocal.get();
    }

    public Long getUserId() {
        return threadLocal.get().getId();
    }

    public String getUserName() {
        return threadLocal.get().getName();
    }
}