package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.common.UserContextInfo;
import com.car.mapper.CarMapper;
import com.car.mapper.OrderMapper;
import com.car.mapper.UserMapper;
import com.car.po.CarPO;
import com.car.po.OrderPO;
import com.car.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 订单操作
 */
@RestController
@RequestMapping("car/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 订单管理界面展示  查询订单
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<OrderPO> select() {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();

        List<OrderPO> orderPOList = orderMapper.selectList(queryWrapper);
        PackResult<OrderPO> result = new PackResult<>();
        result.setDataList(orderPOList);
        return result;
    }

    /**
     * 购买汽车
     * @param carId
     * @return
     */
    @PostMapping("buyCar")
    @ResponseBody
    public PackResult<OrderPO> buyCar(@RequestBody Long carId) {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();

        CarPO carPO = carMapper.selectById(carId);
        UserPO userPO = userMapper.selectById(UserContextInfo.getInstance().getUserId());

        OrderPO orderPO = new OrderPO();
        orderPO.setCarId(carId);
        orderPO.setCarName(carPO.getName());
        orderPO.setUserId(UserContextInfo.getInstance().getUserId());
        orderPO.setUserName(userPO.getName());
        orderPO.setPayStatus("已支付");
        orderPO.setTotalAmount(carPO.getAmount());

        orderMapper.insert(orderPO);
        return new PackResult<>();
    }


    /**
     * 预定汽车
     * @param carId
     * @return
     */
    @PostMapping("reserveCar")
    @ResponseBody
    public PackResult<OrderPO> reserveCar(@RequestBody Long carId) {
        CarPO carPO = carMapper.selectById(carId);
        UserPO userPO = userMapper.selectById(UserContextInfo.getInstance().getUserId());

        OrderPO orderPO = new OrderPO();
        orderPO.setCarId(carId);
        orderPO.setCarName(carPO.getName());
        orderPO.setUserId(UserContextInfo.getInstance().getUserId());
        orderPO.setUserName(userPO.getName());
        orderPO.setPayStatus("已预定");
        orderPO.setTotalAmount(carPO.getAmount());

        orderMapper.insert(orderPO);
        return new PackResult<>();
    }

    /**
     * 查看自己的订单
     */
    @PostMapping("selectByUser")
    @ResponseBody
    public PackResult<OrderPO> selectByUser() {
        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getUserId, UserContextInfo.getInstance().getUserId());
        List<OrderPO> orderPOList = orderMapper.selectList(queryWrapper);

        PackResult<OrderPO> result = new PackResult<>();
        result.setDataList(orderPOList);
        return result;
    }

    /**
     * 支付尾款
     */
    @PostMapping("payFinal")
    @ResponseBody
    public PackResult<String> payFinal(@RequestBody Long orderId) {
        OrderPO orderPO = orderMapper.selectById(orderId);

        if ("已支付".equals(orderPO.getPayStatus())) {
            PackResult.fail("订单已经支付，无需再次支付");
        }

        orderPO.setPayStatus("已支付");
        orderMapper.updateById(orderPO);
        return new PackResult<>();
    }


}