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
                return APPLY_SUCCESSFUL;
            } catch (Exception e) {
                throw new ApiException(ResultCode.FAILED);
            }
        }

        // 接口存在
        // 接口状态为禁用
        if(currentUserInterface.getStatus().equals(USER_INTERFACE_STATUS_DISABLED)){
            throw new ApiException(ResultCode.FAILED, ALREADY_APPLIED);
        }

        return "您已申请过该接口，无需再次申请开通！";
    }

    @Override
    public IPage<UserInterfacePageVO> pageUserInterface(InterfacePageParam param) {
        Long currentUserId = UserHolder.getUser().getId();

        Page<UserInterfacePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);
        QueryWrapper<UserInterfacePageVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(param.getName()), "i.name", param.getName())
                .like(StrUtil.isNotBlank(param.getDescription()), "i.description", param.getDescription())
                .eq("ui.user_id",currentUserId);
        try {
            IPage<UserInterfacePageVO> result = baseMapper.pageInterface(page, queryWrapper);
            return result;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }

}




