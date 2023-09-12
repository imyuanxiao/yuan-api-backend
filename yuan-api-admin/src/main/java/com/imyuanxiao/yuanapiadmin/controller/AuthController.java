package com.imyuanxiao.yuanapiadmin.controller;

import com.imyuanxiao.yuanapiadmin.model.param.LoginRequestParam;
import com.imyuanxiao.yuanapiadmin.model.vo.LoginResponseVO;
import com.imyuanxiao.yuanapiadmin.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description authorization management
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 20:59
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@Api(tags = "登陆注册管理")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    /**
     * @description: register
     * @param param register-related parameters
     * @author imyuanxiao
     */
    @PostMapping("/register")
    @ApiOperation(value = "Register")
    public String register(@RequestBody @Validated LoginRequestParam param){
        return userService.register(param);
    }

    /**
     * @description: login
     * @param param login-related parameters
     * @author imyuanxiao
     */
    @PostMapping("/login")
    @ApiOperation(value = "Login")
    public LoginResponseVO login(@RequestBody @Validated LoginRequestParam param){
        return userService.login(param);
    }

}
