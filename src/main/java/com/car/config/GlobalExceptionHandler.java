package com.car.config;

import com.car.common.BizException;
import com.car.common.PackResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public PackResult bizHandler(BizException e) {
        return PackResult.fail(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public PackResult errorHandler(Exception e) {
        System.out.print(e);
        return PackResult.fail("系统异常");
    }

}
