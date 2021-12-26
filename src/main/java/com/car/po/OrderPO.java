package com.car.po;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order")
public class OrderPO {

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
}