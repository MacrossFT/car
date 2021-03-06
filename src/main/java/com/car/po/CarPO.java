package com.car.po;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("car")
public class CarPO extends BasePO{

    /**
     * 名称
     */
    private String name;

    /**
     * 车辆描述信息
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}