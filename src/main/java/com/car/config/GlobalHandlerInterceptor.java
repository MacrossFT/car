package com.car.config;

import com.car.common.PackResult;
import com.car.common.UserContextInfo;
import com.car.po.UserPO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

public class GlobalHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        公共处理
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            writeResult(response);
            return false;
        }
        UserPO userPO = (UserPO) user;
        UserContextInfo.getInstance().buildUser(userPO);
        return true;
    }

    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private void writeResult(HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 日期设置
        mapper.setDateFormat(fmt);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        PackResult result = new PackResult();
        result.setSuccess(false);
        result.setMessage("请先登录");

        resp.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setStatus(200);
        PrintWriter writer = resp.getWriter();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
    }

}
