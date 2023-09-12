package com.imyuanxiao.yuanapiadmin.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapicommon.model.entity.UserProfile;
import com.imyuanxiao.yuanapiadmin.service.UserProfileService;
import com.imyuanxiao.yuanapiadmin.mapper.UserProfileMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【user_profile(用户资料表（保存非敏感信息）)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

    @Override
    public UserProfile getByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(UserProfile::getUserId, userId).one();
    }

    public boolean updateByUserId(UserProfile userProfile) {

        return lambdaUpdate().eq(ObjUtil.isNotNull(userProfile.getUserId()), UserProfile::getUserId, userProfile.getUserId()).update(userProfile);

    }

}




