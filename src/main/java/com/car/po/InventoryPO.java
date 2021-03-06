package com.car.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("inventory")
public class InventoryPO extends BasePO{

    /**
     * 汽车名称
     */
    @TableField("name")
    private String name;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 价格
     */
    private String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
