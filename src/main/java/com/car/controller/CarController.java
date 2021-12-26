package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.mapper.CarMapper;
import com.car.po.CarPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 汽车管理
 */
@RestController
@RequestMapping("car/car-manage")
public class CarController {

    @Autowired
    private CarMapper carMapper;

    /**
     * 新增汽车 只传name和remark
     * @param carPO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(@RequestBody CarPO carPO) {
        carPO.setNumber(0);
        carMapper.insert(carPO);
        return new PackResult<>();
    }

    /**
     * 删除汽车
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(@RequestBody Long id) {
        carMapper.deleteById(id);
        return new PackResult<>();
    }

    /**
     * 编辑汽车
     * 编辑汽车：传id name remark
     * @param carPO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody CarPO carPO) {
        CarPO carPO1 = carMapper.selectById(carPO.getId());
        carPO1.setName(carPO.getName());
        carPO1.setRemark(carPO.getRemark());

        carMapper.updateById(carPO1);

        return new PackResult<>();
    }

    /**
     * 汽车信息展示
     * @return
     */
    @RequestMapping("select")
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
