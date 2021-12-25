package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.car.common.BizException;
import com.car.common.PackResult;
import com.car.mapper.UserMapper;
import com.car.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController("car/user/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("login")
    @ResponseBody
    public PackResult<String> login(@RequestBody UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserPO::getName, userPO.getName());
        queryWrapper.eq(UserPO::getPassword, userPO.getPassword());
        UserPO user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            return new PackResult<>(user.getPermission());
        } else {
//            throw new BizException("用户名或密码错误，请稍后再试");
            return new PackResult<>(false, "用户名或密码错误，请稍后再试");
        }
    }

    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(@RequestBody UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
        Integer count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
//            throw new BizException("用户名已存在");
        }

        List<UserPO> userPOS = userMapper.selectList(queryWrapper);

        // 1 管理员 2普通用户
        userPO.setPermission("2");
        userPO.setRegTime(new Date());
        int insert = userMapper.insert(userPO);
        return new PackResult<>();
    }

    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(@RequestBody Long id) {
        userMapper.deleteById(id);
        return new PackResult<>();
    }

    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody UserPO userPO) {
        UserPO userPO1 = userMapper.selectById(userPO.getId());

        userPO1.setPassword(userPO.getPassword());
        userPO1.setRemark(userPO.getRemark());
        userMapper.updateById(userPO1);

        return new PackResult<>();
    }

    @PostMapping("select")
    @ResponseBody
    public PackResult<UserPO> select(@RequestBody UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
        queryWrapper.gt(UserPO::getId, 1);

        List<UserPO> userPOS = userMapper.selectList(queryWrapper);
        PackResult<UserPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }

}