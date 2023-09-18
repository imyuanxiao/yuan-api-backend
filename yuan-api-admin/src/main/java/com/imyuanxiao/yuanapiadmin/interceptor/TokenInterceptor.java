package com.imyuanxiao.yuanapiadmin.interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.imyuanxiao.yuanapiadmin.model.vo.UserVO;
import com.imyuanxiao.yuanapiadmin.service.impl.UserServiceImpl;
import com.imyuanxiao.yuanapiadmin.utils.JwtManager;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description 检查token
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 22:38
 **/

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION="Authorization";

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("=========>in TokenInterceptor preHandle");
        String token = request.getHeader(AUTHORIZATION);
        log.info("从请求头中获取的令牌：{}", token);
        if (StrUtil.isBlank(token)) {
            log.info("Token不存在");
            //返回报错信息
            throw new ApiException(ResultCode.INVALID_TOKEN);
            //不放行
        }
        token = token.replace("Bearer ", "");
        // 解析token
        String userID = JwtManager.extractUserID(token);
        // 获取用户信息，存入userHolder
        User user = userService.getById(userID);
        UserVO userVO = userService.getUserVO(user);
        UserHolder.saveUser(userVO);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        log.info("=========>in TokenInterceptor postHandle {} {}");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        log.info("=========>in TokenInterceptor afterCompletion {} {}");
        // 移除 userHolder
        UserHolder.removeUser();
    }

}
