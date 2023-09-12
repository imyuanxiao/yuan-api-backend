package com.imyuanxiao.yuanapiadmin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.yuanapiadmin.model.vo.InterfacePageVO;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import org.apache.ibatis.annotations.Param;

/**
* @author Administrator
* @description 针对表【interface(接口信息表)】的数据库操作Mapper
* @createDate 2023-08-18 21:13:14
* @Entity com.imyuanxiao.yuanapiadmin.model.entity.Interface
*/
public interface InterfaceMapper extends BaseMapper<Interface> {
    IPage<InterfacePageVO> pageInterface(Page<InterfacePageVO> page , @Param(Constants.WRAPPER) Wrapper<InterfacePageVO> wrapper);

}




