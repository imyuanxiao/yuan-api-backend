package com.imyuanxiao.yuanapiadmin.controller;

import com.imyuanxiao.yuanapiadmin.model.param.UserPasswordParam;
import com.imyuanxiao.yuanapiadmin.model.param.UserProfileParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserVO;
import com.imyuanxiao.yuanapiadmin.service.impl.UserServiceImpl;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.imyuanxiao.yuanapicommon.utils.CommonConst.ACTION_SUCCESSFUL;

@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/currentUser")
    @ApiOperation(value = "获取当前用户个人资料")
    public UserVO currentUser(){
        return UserHolder.getUser();
    }

    @PutMapping("/profile")
    @ApiOperation(value = "修改个人资料")
    public String updateProfile(@RequestBody UserProfileParam param){
        userService.updateProfile(param);
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/profile/password")
    @ApiOperation(value = "修改密码")
    public String updatePassword(@RequestBody UserPasswordParam param){
        userService.updatePassword(param);
        return ACTION_SUCCESSFUL;
    }

}
