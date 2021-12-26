package com.car.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("order")
public class OrderPO {

    private Long id;

    /**
     * 用户名
     */
    @TableField("userName")
    private String userName;

    @TableField("userId")
    private Long userId;

    @TableField("carId")
    private Long carId;

    /**
     * 汽车名称
     */
    @TableField("carName")
    private String carName;

    /**
     * 总金额
     */
    @TableField("totalAmount")
    private String totalAmount;

    /**
     * 支付状态
     */
    @TableField("payStatus")
    private String payStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
}