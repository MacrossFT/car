package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.BizException;
import com.car.common.PackResult;
import com.car.common.UserContextInfo;
import com.car.mapper.OrderMapper;
import com.car.mapper.RepairMapper;
import com.car.mapper.UserMapper;
import com.car.po.OrderPO;
import com.car.po.RepairPO;
import com.car.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 售后管理
 */
@RestController
@RequestMapping("car/repair")
public class RepairController {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 查看自己的售后订单
     */
    @PostMapping("selectByUser")
    @ResponseBody
    public PackResult<RepairPO> selectByUser() {
        LambdaQueryWrapper<RepairPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RepairPO::getUserId, UserContextInfo.getInstance().getUserId());
        List<RepairPO> orderPOList = repairMapper.selectList(queryWrapper);

        PackResult<RepairPO> result = new PackResult<>();
        result.setDataList(orderPOList);
        return result;
    }

    /**
     * 添加一个售后问题
     * 只需传orderId，problem
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<RepairPO> add(@RequestBody RepairPO repairPO) {
        OrderPO orderPO = orderMapper.selectById(repairPO.getOrderId());
        repairPO.setCarName(orderPO.getCarName());
        repairPO.setProblem(repairPO.getProblem());
        repairPO.setUserId(UserContextInfo.getInstance().getUserId());
        repairPO.setUserName(UserContextInfo.getInstance().getUserName());
        repairPO.setDealStatus("待处理");

        int insert = repairMapper.insert(repairPO);
        return new PackResult<>();
    }

    /**
     * 提交售后评价
     * 只需穿 id 和 evaluate
     * @param repairPO
     * @return
     */
    @PostMapping("evaluate")
    @ResponseBody
    public PackResult<Boolean> evaluate(@RequestBody RepairPO repairPO) {
        RepairPO repairPO1 = repairMapper.selectById(repairPO.getId());
        repairPO.setEvaluate(repairPO.getEvaluate());
        repairMapper.updateById(repairPO1);
        return new PackResult<>();
    }

    /**
     * 管理员查看所有售后订单
     */
    @RequestMapping("select")
    @ResponseBody
    public PackResult<RepairPO> select() throws BizException {
        Long userId = UserContextInfo.getInstance().getUserId();
        UserPO userPO = userMapper.selectById(userId);
        if ("2".equals(userPO.getPermission())) {
            throw new BizException("无权限查看");
        }

        List<RepairPO> orderPOList = repairMapper.selectList(new LambdaQueryWrapper<>());

        PackResult<RepairPO> result = new PackResult<>();
        result.setDataList(orderPOList);
        return result;
    }

}