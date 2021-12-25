package com.car.dto;

import lombok.Data;

@Data
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
    private int id;
}
