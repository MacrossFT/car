package com.car.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserPO {

    private int id ;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色权限
     * 1：代表管理员
     * 2：代表普通用户
     */
    private String permission;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 备注
     */
    private String remark;
}