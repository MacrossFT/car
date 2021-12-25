package com.car.common;

import lombok.Data;

import java.util.List;

/**
 * 返回结果同一封装
 *
 * @param <T>
 */
@Data
public class PackResult<T> {

    /**
     * 成功标志
     */
    private Boolean success;

    /**
     * 错误提示信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 集合数据
     */
    private List<T> dataList;

    public PackResult() {
        this.success = true;
    }

    public PackResult(T data) {
        this.data = data;
        this.success = true;
    }


    public PackResult(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static PackResult fail(String message) {
        PackResult packResult = new PackResult();
        packResult.setSuccess(false);
        packResult.setMessage(message);
        return packResult;
    }

}