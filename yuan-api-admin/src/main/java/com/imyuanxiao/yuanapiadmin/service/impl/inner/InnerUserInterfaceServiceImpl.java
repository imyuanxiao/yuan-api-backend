package com.imyuanxiao.yuanapiadmin.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapiadmin.mapper.UserInterfaceMapper;

import com.imyuanxiao.yuanapiadmin.service.InterfaceService;
import com.imyuanxiao.yuanapiadmin.service.UserService;

import com.imyuanxiao.yuanapiadmin.service.impl.InterfaceServiceImpl;
import com.imyuanxiao.yuanapiadmin.service.impl.UserServiceImpl;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import com.imyuanxiao.yuanapicommon.model.entity.User;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;
import com.imyuanxiao.yuanapicommon.service.InnerUserInterfaceService;
import com.imyuanxiao.yuanapicommon.utils.CommonConst;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
* @author Administrator
* @description 针对表【user_interface(用户_接口关系表)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@DubboService
public class InnerUserInterfaceServiceImpl extends ServiceImpl<UserInterfaceMapper, UserInterface>
    implements InnerUserInterfaceService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserInterface getInvokeUserInterface(String accessKey) {
        try {
            UserInterface userInterface = this.lambdaQuery()
                    .eq(UserInterface::getAccessKey, accessKey).one();
            // 接口授权是否正常
            if(userInterface == null || userInterface.getStatus().equals(CommonConst.USER_INTERFACE_STATUS_DISABLED)){
                throw new ApiException(ResultCode.FAILED, "无权调用该接口！");
            }
            // 接口剩余可调用次数
            if(userInterface.getLeftNum() <= 0){
                throw new ApiException(ResultCode.FAILED, "接口剩余次数不足！");
            }
            // 用户是否存在或用户状态是否异常
            User user = userService.getById(userInterface.getUserId());
            if(user == null || user.getStatus().equals(CommonConst.USER_STATUS_DISABLED)){
                throw new ApiException(ResultCode.FAILED, "用户不存在或状态异常！");
            }
            return userInterface;
        } catch (ApiException e) {
            throw e; // 如果是自定义的 ApiException，直接抛出
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }

    @Override
    public boolean subCallNum(long interfaceId, long userId) {
        LambdaUpdateWrapper<UserInterface> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInterface::getInterfaceId, interfaceId)
                .eq(UserInterface::getUserId, userId)
                .setSql("left_num = left_num - 1");
        int rowsUpdated = baseMapper.update(null, updateWrapper);
        return rowsUpdated > 0;
    }

    @Override
    public boolean hasLeftNum(Long interfaceId, Long userId) {
        Integer leftNum = this.lambdaQuery()
                .eq(UserInterface::getInterfaceId, interfaceId)
                .eq(UserInterface::getUserId, userId).one().getLeftNum();
        return leftNum > 0;
    }

}




