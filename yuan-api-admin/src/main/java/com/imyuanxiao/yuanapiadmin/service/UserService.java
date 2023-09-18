package com.imyuanxiao.yuanapiadmin.service;

import com.imyuanxiao.yuanapiadmin.model.param.UserPasswordParam;
import com.imyuanxiao.yuanapiadmin.model.param.UserProfileParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserVO;
import com.imyuanxiao.yuanapicommon.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.yuanapiadmin.model.param.LoginRequestParam;
import com.imyuanxiao.yuanapiadmin.model.vo.LoginResponseVO;

/**
* @author Administrator
* @description 针对表【user(用户表（保存敏感信息）)】的数据库操作Service
* @createDate 2023-08-18 21:13:14
*/
public interface UserService extends IService<User> {
    /**
     * @description Register
     * @author imyuanxiao
     * @param param Register form parameters
     **/
    String register(LoginRequestParam param);
    /**
     * @description Log in
     * @author imyuanxiao
     * @param param Login form parameters
     **/
    LoginResponseVO login(LoginRequestParam param);

    void updateProfile(UserProfileParam param);

    void updatePassword(UserPasswordParam param);
}
