package com.car.dto;

public class CarInventoryDTO {

    /**
     * 数量
     */
    private Integer number;

    /**
     * 价格
     */
    private String amount;

    /**
     * 车辆id
     */
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
