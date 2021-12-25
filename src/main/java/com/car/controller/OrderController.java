package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.mapper.OrderMapper;
import com.car.po.OrderPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("car/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("select")
    @ResponseBody
    public PackResult<OrderPO> select() {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();

        List<OrderPO> orderPOList = orderMapper.selectList(queryWrapper);
        PackResult<OrderPO> result = new PackResult<>();
        result.setDataList(orderPOList);
        return result;
    }

    @PostMapping("byCar")
    @ResponseBody
    public PackResult<OrderPO> byCar() {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();


        return new PackResult<>();
    }



}