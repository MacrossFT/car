package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.BizException;
import com.car.common.PackResult;
import com.car.common.UserContextInfo;
import com.car.dto.OrderDTO;
import com.car.mapper.CarMapper;
import com.car.mapper.InventoryMapper;
import com.car.mapper.OrderMapper;
import com.car.mapper.UserMapper;
import com.car.po.CarPO;
import com.car.po.InventoryPO;
import com.car.po.OrderPO;
import com.car.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private InventoryMapper inventoryMapper;

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
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<OrderPO> buyCar(@RequestBody OrderDTO dto) {
        CarPO carPO = carMapper.selectById(dto.getCarId());

        InventoryPO inventoryPO = inventoryMapper.selectById(dto.getCarId());

        if (inventoryPO.getNumber() < 1) {
            throw new BizException("汽车数量小于0，请去预定");
        }
        inventoryPO.setNumber(inventoryPO.getNumber() - 1);
        inventoryMapper.updateById(inventoryPO);

        UserPO userPO = userMapper.selectById(UserContextInfo.getInstance().getUserId());

        OrderPO orderPO = new OrderPO();
        orderPO.setCarId(dto.getCarId());
        orderPO.setCarName(carPO.getName());
        orderPO.setUserId(UserContextInfo.getInstance().getUserId());
        orderPO.setUserName(userPO.getName());
        orderPO.setPayStatus("已支付");
        orderPO.setTotalAmount(inventoryPO.getAmount());

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
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<OrderPO> reserveCar(@RequestBody Long carId) {
        CarPO carPO = carMapper.selectById(carId);
        InventoryPO inventoryPO = inventoryMapper.selectById(carId);
        if (inventoryPO.getNumber() > 0) {
            throw new BizException("汽车数量大于0，请直接购买");
        }
        UserPO userPO = userMapper.selectById(UserContextInfo.getInstance().getUserId());

        OrderPO orderPO = new OrderPO();
        orderPO.setCarId(carId);
        orderPO.setCarName(carPO.getName());
        orderPO.setUserId(UserContextInfo.getInstance().getUserId());
        orderPO.setUserName(userPO.getName());
        orderPO.setPayStatus("已预定");
        orderPO.setTotalAmount(inventoryPO.getAmount());

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
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<String> payFinal(@RequestBody OrderDTO dto) {
        OrderPO orderPO = orderMapper.selectById(dto.getOrderId());
        InventoryPO inventoryPO = inventoryMapper.selectById(orderPO.getCarId());
        CarPO carPO = carMapper.selectById(orderPO.getCarId());
        if ("已支付".equals(orderPO.getPayStatus())) {
            PackResult.fail("订单已经支付，无需再次支付");
        }

        if (inventoryPO.getNumber() < 1) {
            throw new BizException(carPO.getName() + "汽车数量不足，请稍后再试");
        }

        inventoryPO.setNumber(inventoryPO.getNumber() - 1);
        inventoryMapper.updateById(inventoryPO);

        orderPO.setPayStatus("已支付");
        orderMapper.updateById(orderPO);
        return new PackResult<>();
    }


}