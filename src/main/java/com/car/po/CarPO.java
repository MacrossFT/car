package com.car.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("car")
public class CarPO {

    private String name;

    private int number;

    private String amount;

    private String remark;

    private int id;
}