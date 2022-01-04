package com.car.dto;

public class CarDTO {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 车辆描述信息
     */
    private String remark;

    /**
     * 数量
     */
    private Integer number;

    /**
     * 价格
     */
    private String amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
