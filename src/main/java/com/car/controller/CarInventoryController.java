package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.dto.CarInventoryDTO;
import com.car.mapper.CarMapper;
import com.car.po.CarPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @menu 车库存管理
 */
@RestController
@RequestMapping("car/car-inventory")
public class CarInventoryController {

    @Autowired
    private CarMapper carMapper;

    /**
     * 编辑汽车库存
     * 修改汽车库存：传id number amount
     * @param dto
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody CarInventoryDTO dto) {
        CarPO carPO1 = carMapper.selectById(dto.getId());
        carPO1.setAmount(dto.getAmount());
        carPO1.setNumber(dto.getNumber());

        carMapper.updateById(carPO1);

        return new PackResult<>();
    }

    /**
     * 库存展示
     * @return
     */
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
