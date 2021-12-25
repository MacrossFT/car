package com.car.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class UserPO {

    private int id ;     //id

    private String name;  //姓名

    private String password;  //密码

    private int permission;    //角色

    //注册时间
    private Date regTime;
}