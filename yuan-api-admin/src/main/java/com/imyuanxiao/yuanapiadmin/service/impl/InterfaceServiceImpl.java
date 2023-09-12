package com.imyuanxiao.yuanapiadmin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.param.InterfaceRequestParam;
import com.imyuanxiao.yuanapiadmin.model.vo.InterfacePageVO;
import com.imyuanxiao.yuanapiadmin.model.vo.UserVO;
import com.imyuanxiao.yuanapiadmin.service.InterfaceService;
import com.imyuanxiao.yuanapiadmin.mapper.InterfaceMapper;
import com.imyuanxiao.yuanapiadmin.utils.UserHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
* @author Administrator
* @description 针对表【interface(接口信息表)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@Service
public class InterfaceServiceImpl extends ServiceImpl<InterfaceMapper, Interface>
    implements InterfaceService {

    @Override
    public void addInterface(InterfaceRequestParam param) {
        Interface newInterface = BeanUtil.copyProperties(param, Interface.class);
        UserVO currentUser = UserHolder.getUser();
        newInterface.setUserId(currentUser.getId());
        try {
            this.save(newInterface);
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }

    @Override
    public void deleteInterface(Long[] ids) {
        try {
            this.removeBatchByIds(Arrays.asList(ids));
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }

    @Override
    public void updateInterface(InterfaceRequestParam param) {
        try {
            Interface updatedInterface = BeanUtil.copyProperties(param, Interface.class);
            this.updateById(updatedInterface);
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }

    @Override
    public void setInterfaceStatusById(Long id, int status) {
        // 0-关闭，1-开启
        try {
            this.lambdaUpdate().eq(Interface::getId, id).set(Interface::getStatus, status).update();
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }

    }

    @Override
    public IPage<InterfacePageVO> pageInterface(InterfacePageParam param) {
        Page<InterfacePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("id");
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);
        QueryWrapper<InterfacePageVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(param.getName()), "name", param.getName())
                .like(StrUtil.isNotBlank(param.getDescription()), "description", param.getDescription());

        try {
            IPage<InterfacePageVO> pageData = baseMapper.pageInterface(page, queryWrapper);
            return pageData;
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }

    }

}




