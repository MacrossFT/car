package com.car.common;

import java.io.Serializable;
import java.util.List;

/**
 * 返回结果同一封装
 *
 * @param <T>
 */
public class PackResult<T> implements Serializable {

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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}