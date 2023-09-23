package com.imyuanxiao.yuanapiadmin.service;

import com.imyuanxiao.yuanapicommon.model.entity.UserInterfaceHistory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【user_interface_history(用户_接口调用历史表)】的数据库操作Service
* @createDate 2023-08-18 21:13:14
*/
public interface UserInterfaceHistoryService extends IService<UserInterfaceHistory> {

    List<Integer> getChartCallTrend();
}
