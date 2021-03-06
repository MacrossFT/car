package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
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
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserPO::getName, UserContextInfo.getInstance().getUserName());
        queryWrapper.eq(UserPO::getPassword, dto.getPassword());
        UserPO user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BizException("密码输入错误,请重新输入");
        }

        CarPO carPO = carMapper.selectById(dto.getCarId());

        InventoryPO inventoryPO = inventoryMapper.selectById(dto.getCarId());

        if (StringUtils.isEmpty(inventoryPO.getAmount())) {
            throw new BizException("汽车价格未发布，暂时无法购买");
        }

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
     * @param dto
     * @return
     */
    @PostMapping("reserveCar")
    @ResponseBody
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<OrderPO> reserveCar(@RequestBody OrderDTO dto) {
        CarPO carPO = carMapper.selectById(dto.getCarId());
        InventoryPO inventoryPO = inventoryMapper.selectById(dto.getCarId());
        if (inventoryPO.getNumber() > 0) {
            throw new BizException("汽车数量大于0，请直接购买");
        }
        if (StringUtils.isEmpty(inventoryPO.getAmount())) {
            throw new BizException("汽车价格未发布，暂时无法购买");
        }

        LambdaQueryWrapper<OrderPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderPO::getUserId, UserContextInfo.getInstance().getUserId());
        queryWrapper.eq(OrderPO::getPayStatus, "已预定");
        List<OrderPO> orderPOS = orderMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(orderPOS)) {
            throw new BizException("你有未支付的订单  请先支付后在进行预定");
        }

        UserPO userPO = userMapper.selectById(UserContextInfo.getInstance().getUserId());

        OrderPO orderPO = new OrderPO();
        orderPO.setCarId(dto.getCarId());
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
        LambdaQueryWrapper<UserPO> userPOLambdaQueryWrapper = new LambdaQueryWrapper();
        userPOLambdaQueryWrapper.eq(UserPO::getName, UserContextInfo.getInstance().getUserName());
        userPOLambdaQueryWrapper.eq(UserPO::getPassword, dto.getPassword());
        UserPO user = userMapper.selectOne(userPOLambdaQueryWrapper);
        if (user == null) {
            throw new BizException("密码输入错误,请重新输入");
        }

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