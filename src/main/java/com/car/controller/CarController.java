package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.mapper.CarMapper;
import com.car.po.CarPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("car/car-manage/")
public class CarController {

    @Autowired
    private CarMapper carMapper;


    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(@RequestBody CarPO carPO) {
        carMapper.insert(carPO);
        return new PackResult<>();
    }

    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(@RequestBody Long id) {
        carMapper.deleteById(id);
        return new PackResult<>();
    }

    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody CarPO carPO) {
        CarPO carPO1 = carMapper.selectById(carPO.getId());
        carPO1.setAmount(carPO.getAmount());
        carPO1.setNumber(carPO.getNumber());
        carPO1.setRemark(carPO.getRemark());

        carMapper.updateById(carPO1);

        return new PackResult<>();
    }

    @PostMapping("select")
    @ResponseBody
    public PackResult<CarPO> select() {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
//        queryWrapper.gt(CarPO::getId, 1);

        List<CarPO> userPOS = carMapper.selectList(queryWrapper);
        PackResult<CarPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }

}
