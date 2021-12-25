package com.car.config;

import com.car.common.BizException;
import com.car.common.UserContextInfo;
import com.car.po.UserPO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GlobalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //公共处理
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            throw new BizException("请先登录");
        }
        UserPO userPO = (UserPO) user;
        UserContextInfo.getInstance().buildUser(userPO);
        return true;
    }

}
