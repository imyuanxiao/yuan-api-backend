package com.imyuanxiao.yuanapiadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterfaceHistory;
import com.imyuanxiao.yuanapiadmin.service.UserInterfaceHistoryService;
import com.imyuanxiao.yuanapiadmin.mapper.UserInterfaceHistoryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【user_interface_history(用户_接口调用历史表)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@Service
public class UserInterfaceHistoryServiceImpl extends ServiceImpl<UserInterfaceHistoryMapper, UserInterfaceHistory>
    implements UserInterfaceHistoryService {

    @Override
    public List<Integer> getChartCallTrend() {
        LocalDateTime now = LocalDateTime.now();
        List<Integer> result = new ArrayList<>();
        // 开始日期：往前数5天
        for(int i = 0; i < 5; i++){
            LocalDateTime startTime = now.minusDays(5-i);;
            LocalDateTime endTime = startTime.plusDays(1);
            // 使用 MyBatis-Plus 构建查询条件
            Long count = this.lambdaQuery()
                    .ge(UserInterfaceHistory::getCreatedTime, startTime)
                    .lt(UserInterfaceHistory::getCreatedTime, endTime)
                    .count();
            result.add(count.intValue());
        }
        return result;
    }
}




