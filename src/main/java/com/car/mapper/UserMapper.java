package com.car.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.car.po.UserPO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<UserPO> {

}