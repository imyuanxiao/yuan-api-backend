package com.imyuanxiao.yuanapiadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.param.InterfaceRequestParam;
import com.imyuanxiao.yuanapiadmin.model.vo.InterfacePageVO;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;

/**
* @author Administrator
* @description 针对表【interface(接口信息表)】的数据库操作Service
* @createDate 2023-08-18 21:13:14
*/
public interface InterfaceService extends IService<Interface> {

    void addInterface(InterfaceRequestParam param);

    void deleteInterface(Long[] ids);

    void updateInterface(InterfaceRequestParam param);

    IPage<InterfacePageVO> pageInterface( InterfacePageParam param);

    void setInterfaceStatusById(Long id, int status);
}
