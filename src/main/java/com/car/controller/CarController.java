package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.car.common.PackResult;
import com.car.dto.CarDTO;
import com.car.mapper.CarMapper;
import com.car.mapper.InventoryMapper;
import com.car.po.CarPO;
import com.car.po.InventoryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @menu 汽车管理
 */
@RestController
@RequestMapping("car/car-manage")
public class CarController {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private InventoryMapper inventoryMapper;

    /**
     * 新增汽车 只传name和remark
     * @param carPO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<Boolean> add(@RequestBody CarPO carPO) {
        InventoryPO inventoryPO = new InventoryPO();
        inventoryPO.setNumber(0);
        inventoryPO.setName(carPO.getName());
        inventoryMapper.insert(inventoryPO);
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
    @Transactional(rollbackFor = Throwable.class)
    public PackResult<Boolean> delete(@RequestBody CarPO carPO) {
        carMapper.deleteById(carPO.getId());
        inventoryMapper.deleteById(carPO.getId());
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

        InventoryPO inventoryPO = inventoryMapper.selectById(carPO.getId());
        inventoryPO.setName(carPO1.getName());
        inventoryMapper.updateById(inventoryPO);
        return new PackResult<>();
    }

    /**
     * 汽车信息展示
     * @return
     */
    @RequestMapping("select")
    @ResponseBody
    public PackResult<CarDTO> select() {
        LambdaQueryWrapper<CarPO> queryWrapper = new LambdaQueryWrapper<>();

        List<CarPO> carPOList = carMapper.selectList(queryWrapper);

        List<CarDTO> carDTOList = new ArrayList<>();
        for (CarPO carPO : carPOList) {
            CarDTO carDTO = new CarDTO();
            carDTO.setId(carPO.getId());
            carDTO.setName(carPO.getName());
            carDTO.setRemark(carPO.getRemark());
            InventoryPO inventoryPO = inventoryMapper.selectById(carPO.getId());
            carDTO.setNumber(inventoryPO.getNumber());
            carDTO.setAmount(inventoryPO.getAmount());
            carDTOList.add(carDTO);
        }


        PackResult<CarDTO> result = new PackResult<>();
        result.setDataList(carDTOList);
        return result;
    }

}
