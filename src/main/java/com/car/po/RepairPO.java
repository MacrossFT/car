package com.car.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
}