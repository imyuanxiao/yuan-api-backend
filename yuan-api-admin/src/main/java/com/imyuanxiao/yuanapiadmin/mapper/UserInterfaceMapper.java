package com.imyuanxiao.yuanapiadmin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.yuanapiadmin.model.vo.InterfacePageVO;
import com.imyuanxiao.yuanapiadmin.model.vo.UserInterfacePageVO;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【user_interface(用户_接口关系表)】的数据库操作Mapper
* @createDate 2023-08-18 21:13:14
* @Entity com.imyuanxiao.yuanapiadmin.model.entity.UserInterface
*/
public interface UserInterfaceMapper extends BaseMapper<UserInterface> {
    IPage<UserInterfacePageVO> pageInterface(Page<UserInterfacePageVO> page , @Param(Constants.WRAPPER) Wrapper<UserInterfacePageVO> wrapper);

}




