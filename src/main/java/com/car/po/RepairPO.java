package com.car.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair")
public class RepairPO {

    private Long id;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 用户ID
     */
    private Long UserId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 车辆名称
     */
    private String carName;

    /**
     * 问题描述
     */
    private String problem;

    /**
     * 处理状态
     */
    private String dealStatus;

    /**
     * 评价
     */
    private String evaluate;

}