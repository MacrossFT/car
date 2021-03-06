package com.car.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.car.common.BizException;
import com.car.common.PackResult;
import com.car.common.UserContextInfo;
import com.car.dto.UserRespDTO;
import com.car.mapper.UserMapper;
import com.car.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @menu 用户管理
 */
@RestController
@RequestMapping("car/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登陆接口 只传name和password
     * 返回值权限取 permission
     *
     * @param userPO
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public PackResult<UserPO> login(HttpServletResponse response, HttpSession session, @RequestBody UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserPO::getName, userPO.getName());
        queryWrapper.eq(UserPO::getPassword, userPO.getPassword());
        UserPO user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("user", user);
            return new PackResult<>(user);
        } else {
            return new PackResult<>(false, "用户名或密码错误，请稍后再试");
        }
    }

    /**
     * 获取用户信息接口
     *
     * @return
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    private PackResult<UserRespDTO> getUserInfo() {
        UserPO user = UserContextInfo.getInstance().getUser();
        UserRespDTO dto = new UserRespDTO();
        dto.setName(user.getName());
        dto.setRoles(Collections.singletonList(user.getPermission()));
        return new PackResult<>(dto);
    }


    /**
     * 退出登陆
     *
     * @param session
     * @return
     */
    @RequestMapping("logout")
    public PackResult<Boolean> logout(HttpSession session) {
        session.removeAttribute("user");
        return new PackResult<>();
    }

    /**
     * 注册接口 只传name和password
     *
     * @param userPO
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public PackResult<Boolean> add(@RequestBody UserPO userPO) {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
        Integer count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BizException("用户名已存在");
        }

        List<UserPO> userPOS = userMapper.selectList(queryWrapper);

        // 1 管理员 2普通用户
        userPO.setPermission("2");
        userPO.setRegTime(new Date());
        int insert = userMapper.insert(userPO);
        return new PackResult<>();
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public PackResult<Boolean> delete(@RequestBody Long id) {
        userMapper.deleteById(id);
        return new PackResult<>();
    }

    /**
     * 更新密码 只传id，password
     *
     * @param userPO
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public PackResult<Boolean> update(@RequestBody UserPO userPO) {
        UserPO userPO1 = userMapper.selectById(userPO.getId());

        userPO1.setPassword(userPO.getPassword());
        userPO1.setRemark(userPO.getRemark());
        userMapper.updateById(userPO1);

        return new PackResult<>();
    }

    /**
     * 用户列表展示
     *
     * @return
     */
    @PostMapping("select")
    @ResponseBody
    public PackResult<UserPO> select() {
        LambdaQueryWrapper<UserPO> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(StringUtils.isNotEmpty(userPO.getName()), UserPO::getName, userPO.getName());
        queryWrapper.gt(UserPO::getId, 1);

        List<UserPO> userPOS = userMapper.selectList(queryWrapper);
        PackResult<UserPO> result = new PackResult<>();
        result.setDataList(userPOS);
        return result;
    }

}