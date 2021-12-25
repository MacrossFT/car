package com.car.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("car")
public class CarPO {

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private int number;

    /**
     * 价格
     */
    private String amount;

    private String remark;

    private int id;
}