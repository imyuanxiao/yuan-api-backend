package com.imyuanxiao.yuanapiadmin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserInterfacePageVO;

/**
* @author Administrator
* @description 针对表【user_interface(用户_接口关系表)】的数据库操作Service
* @createDate 2023-08-18 21:13:14
*/
public interface UserInterfaceService extends IService<UserInterface> {

    String apply(Long id);

    IPage<UserInterfacePageVO> pageUserInterface(InterfacePageParam param);

}
