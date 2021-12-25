package com.car.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order")
public class OrderPO {

    /**
     * 用户名
     */
    private String userName;

    private String userId;

    private String carId;

    /**
     * 汽车名称
     */
    private String carName;

    /**
     * 总金额
     */
    private String totalAmount;

    /**
     * 支付状态
     */
    private String payStatus;
}