package com.imyuanxiao.yuanapiadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapiadmin.model.param.UserPasswordParam;
import com.imyuanxiao.yuanapiadmin.model.param.UserProfileParam;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.User;
import com.imyuanxiao.yuanapicommon.model.entity.UserProfile;
import com.imyuanxiao.yuanapiadmin.model.param.LoginRequestParam;
import com.imyuanxiao.yuanapiadmin.model.vo.LoginResponseVO;
import com.imyuanxiao.yuanapiadmin.model.vo.UserVO;
import com.imyuanxiao.yuanapiadmin.service.UserService;
import com.imyuanxiao.yuanapiadmin.mapper.UserMapper;
import com.imyuanxiao.yuanapicommon.utils.CommonConst;
import com.imyuanxiao.yuanapiadmin.utils.JwtManager;
import com.imyuanxiao.yuanapiadmin.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.imyuanxiao.yuanapicommon.utils.CommonConst.ACTION_SUCCESSFUL;

/**
* @author Administrator
* @description 针对表【user(用户表（保存敏感信息）)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private UserProfileServiceImpl userProfileService;

    @Override
    public String register(LoginRequestParam param) {

/*        // 密码注册
        if (StrUtil.isBlank(param.getPassword())) {
            throw new ApiException(ResultCode.PARAMS_ERROR, "密码格式错误！");
        }*/
        // 用户名密码注册
        User user = new User()
                .setUsername(param.getUsername())
                .setPassword(securityUtil.encodePassword(param.getPassword()));
        try {
            boolean save = this.save(user);
            if(!save){
                throw new ApiException(ResultCode.FAILED);
            }
            return ACTION_SUCCESSFUL;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "用户名已存在！");
        }
    }

    @Override
    public LoginResponseVO login(LoginRequestParam param) {

        String username = param.getUsername();
        User userResult = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(username), User::getUsername, username)
                .one();
        if (userResult == null || !securityUtil.matches(param.getPassword(), userResult.getPassword())) {
            throw new ApiException(ResultCode.VALIDATE_FAILED, "用户名或密码错误！");
        }

        // If state is abnormal
        if (userResult.getStatus() != CommonConst.USER_STATUS_NORMAL) {
            throw new ApiException(ResultCode.ACCOUNT_STATE_DISABLED);
        }
        return new LoginResponseVO().setToken(JwtManager.generate(userResult.getUsername(), userResult.getId()));
    }

    @Override
    public void updateProfile(UserProfileParam param) {

        boolean update = this.lambdaUpdate().eq(User::getId, param.getId())
                .set(StrUtil.isNotBlank(param.getUsername()), User::getUsername, param.getUsername())
                .set(StrUtil.isNotBlank(param.getPhone()), User::getPhone, param.getPhone())
                .set(StrUtil.isNotBlank(param.getEmail()), User::getEmail, param.getEmail())
                .update();
        if(!update){
            throw new ApiException(ResultCode.FAILED);
        }

        if(StrUtil.isNotBlank(param.getNickName())){
            UserProfile userProfile = new UserProfile().setUserId(param.getId()).setNickName(param.getNickName());
            boolean result = userProfileService.updateByUserId(userProfile);
            if(!result){
                throw new ApiException(ResultCode.FAILED);
            }
        }

    }

    @Override
    public void updatePassword(UserPasswordParam param) {
        Long userId = UserHolder.getUser().getId();
        // 检查旧密码
        User user = this.getById(userId);
        boolean matches = securityUtil.matches(param.getOldPassword(), user.getPassword());
        if(!matches){
            throw new ApiException(ResultCode.FAILED,"原密码错误！");
        }
        // 更新新密码
        boolean update = this.lambdaUpdate().eq(User::getId, userId).set(User::getPassword, securityUtil.encodePassword(param.getNewPassword())).update();
        if(!update){
            throw new ApiException(ResultCode.FAILED);
        }
    }

    public UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        // Copy basic info
        BeanUtil.copyProperties(user, userVO);
        // Copy user profile
        UserProfile userProfile = userProfileService.getByUserId(user.getId());
        // Initialize user profile if new user
        if (userProfile == null) {
            userProfile = new UserProfile()
                    .setNickName(user.getUsername() + user.getId())
                    .setAvatar("https://img1.imgtp.com/2023/06/12/OMedYC4F.jpg");
            userProfile.setUserId(user.getId());
            userProfileService.save(userProfile);
        }
        // 将userProfile数据拷贝到userVO，但是忽视id
        BeanUtil.copyProperties(userProfile, userVO, "id", "userID");
        return userVO;
    }


}




