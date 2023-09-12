package com.imyuanxiao.yuanapiadmin.interceptor;

import com.imyuanxiao.yuanapiadmin.annotation.AuthCheck;
import com.imyuanxiao.yuanapiadmin.service.impl.UserServiceImpl;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @description 权限检查AOP
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 22:29
 **/
@Aspect
@Component
public class AuthInterceptor {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 带有authCheck注解的方法会在方法前后进行权限检查
     * @param joinPoint
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        // 进行权限检查
        List<String> roles = Arrays.stream(authCheck.mustRole()).toList();

        // 通过userService获取用户信息
        String userRole = userService.getById(UserHolder.getUser().getId()).getRole();

        // 用户权限不足，抛异常
        if(!roles.contains(userRole)){
            throw new ApiException(ResultCode.FORBIDDEN);
        }

        // 如果权限检查通过，继续执行目标方法
        return joinPoint.proceed();
    }

}
