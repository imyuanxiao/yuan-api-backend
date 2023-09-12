package com.imyuanxiao.yuanapiadmin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserInterfacePageVO;
import com.imyuanxiao.yuanapiadmin.service.UserInterfaceService;
import com.imyuanxiao.yuanapiadmin.mapper.UserInterfaceMapper;
import com.imyuanxiao.yuanapiadmin.utils.KeyUtil;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.imyuanxiao.yuanapicommon.utils.CommonConst.*;

/**
* @author Administrator
* @description 针对表【user_interface(用户_接口关系表)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@Service
public class UserInterfaceServiceImpl extends ServiceImpl<UserInterfaceMapper, UserInterface>
    implements UserInterfaceService {

    @Autowired
    private InterfaceServiceImpl interfaceService;

    @Override
    public String apply(Long interfaceId) {

        // 先判断申请的接口是否存在
        if(interfaceService.getById(interfaceId) == null){
            throw new ApiException(ResultCode.INTERFACE_NOT_FOUND);
        }

        Long userId = UserHolder.getUser().getId();
        // 查询接口
        UserInterface currentUserInterface = this.lambdaQuery().eq(UserInterface::getUserId, userId).eq(UserInterface::getInterfaceId, interfaceId).one();
        // 接口不存在，新增接口
        if(currentUserInterface == null){
            UserInterface userInterface = new UserInterface().setInterfaceId(interfaceId).setUserId(userId)
                    .setAccessKey(KeyUtil.generateAccessKey()).setSecretKey(KeyUtil.generateSecretKey())
                    .setLeftNum(DEFAULT_INTERFACE_CALL_NUM).setTotalNum(DEFAULT_INTERFACE_CALL_NUM);
            try {
                this.save(userInterface);
                return ACTION_SUCCESSFUL;
            } catch (Exception e) {
                throw new ApiException(ResultCode.FAILED);
            }
        }

        // 接口存在
        // 接口状态为禁用
        if(currentUserInterface.getStatus().equals(USER_INTERFACE_STATUS_DISABLED)){
            throw new ApiException(ResultCode.USER_INTERFACE_DISABLED);
        }

        return ALREADY_APPLIED;
    }

    //todo 改为加
    @Override
    public String setCallNum(Long interfaceId, Integer type) {

        Long userId = UserHolder.getUser().getId();
        UserInterface currentUserInterface = this.lambdaQuery().eq(UserInterface::getUserId, userId).eq(UserInterface::getInterfaceId, interfaceId).one();
        if(currentUserInterface == null){
            throw new ApiException(ResultCode.USER_INTERFACE_NOT_APPLIED);
        }

        if(currentUserInterface.getStatus().equals(USER_INTERFACE_STATUS_DISABLED)){
            throw new ApiException(ResultCode.USER_INTERFACE_DISABLED);
        }
        try {
            if(type.equals(USER_INTERFACE_CALL_NUM_ADD)){
                this.lambdaUpdate().eq(UserInterface::getInterfaceId, interfaceId).eq(UserInterface::getUserId, userId)
                        .set(UserInterface::getLeftNum, currentUserInterface.getLeftNum() + DEFAULT_INTERFACE_CALL_NUM)
                        .set(UserInterface::getTotalNum, currentUserInterface.getTotalNum() + DEFAULT_INTERFACE_CALL_NUM)
                        .update();
            }
            if(type.equals(USER_INTERFACE_CALL_NUM_SUB)) {
                Integer leftNum = currentUserInterface.getLeftNum();
                if(leftNum > 0){
                    leftNum--;
                }
                this.lambdaUpdate().eq(UserInterface::getInterfaceId, interfaceId).eq(UserInterface::getUserId, userId)
                        .set(UserInterface::getLeftNum, leftNum).update();
            }
            return ACTION_SUCCESSFUL;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }


    @Override
    public IPage<UserInterfacePageVO> pageUserInterface(InterfacePageParam param) {
        Page<UserInterfacePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);
        QueryWrapper<UserInterfacePageVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(param.getName()), "i.name", param.getName())
                .like(StrUtil.isNotBlank(param.getDescription()), "i.description", param.getDescription());

        try {
            IPage<UserInterfacePageVO> result = baseMapper.pageInterface(page, queryWrapper);
            return result;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }

    }

    @Override
    public boolean checkAuth(Long interfaceId, String accessKey, String secretKey) {
        UserInterface one = this.lambdaQuery().eq(UserInterface::getId, interfaceId)
                .eq(UserInterface::getAccessKey, accessKey)
                .eq(UserInterface::getSecretKey, secretKey).one();
        if(one == null){
            throw new ApiException(ResultCode.USER_INTERFACE_NOT_APPLIED);
        }
        if(one.getStatus().equals(USER_INTERFACE_STATUS_DISABLED)){
            throw new ApiException(ResultCode.USER_INTERFACE_DISABLED);
        }
        return true;
    }

}




