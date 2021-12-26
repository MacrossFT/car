package com.car.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair")
public class RepairPO {

    private Long id;

    /**
     * 订单ID
     */
    @TableField("orderId")
    private Long orderId;

    /**
     * 用户ID
     */
    @TableField("userId")
    private Long userId;

    /**
     * 用户名称
     */
    @TableField("userName")
    private String userName;

    /**
     * 车辆名称
     */
    @TableField("carName")
    private String carName;

    /**
     * 问题描述
     */
    private String problem;

    /**
     * 处理状态
     */
    @TableField("dealStatus")
    private String dealStatus;

    /**
     * 评价
     */
    private String evaluate;

}