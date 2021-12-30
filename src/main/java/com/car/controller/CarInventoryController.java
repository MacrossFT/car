package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.car.common.PackResult;
import com.car.dto.CarInventoryDTO;
import com.car.mapper.CarMapper;
import com.car.mapper.InventoryMapper;
import com.car.po.CarPO;
import com.car.po.InventoryPO;
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
    private InventoryMapper inventoryMapper;

    /**
     * 编辑汽车库存
     * 修改汽车库存：传id number amount
     *
     * @param inventoryPO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody InventoryPO inventoryPO) {

        inventoryMapper.updateById(inventoryPO);

        return new PackResult<>();
    }

    /**
     * 库存展示
     *
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<InventoryPO> select() {
        List<InventoryPO> userPOS = inventoryMapper.selectList(new QueryWrapper<>());
        PackResult<InventoryPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }

}
