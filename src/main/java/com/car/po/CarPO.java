package com.car.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("repair")
public class CarPO {

    /**
     * 名称
     */
    private String name;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 价格
     */
    private String amount;

    /**
     * 车辆描述信息
     */
    private String remark;

    private int id;
}