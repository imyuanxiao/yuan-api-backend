package com.imyuanxiao.yuanapiadmin.service;

import com.imyuanxiao.yuanapicommon.model.entity.UserProfile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Administrator
* @description 针对表【user_profile(用户资料表（保存非敏感信息）)】的数据库操作Service
* @createDate 2023-08-18 21:13:14
*/
public interface UserProfileService extends IService<UserProfile> {

    UserProfile getByUserId(Long userId);

    boolean updateByUserId(UserProfile userProfile);

}
