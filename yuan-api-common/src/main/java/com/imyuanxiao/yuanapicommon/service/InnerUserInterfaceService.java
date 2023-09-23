package com.imyuanxiao.yuanapicommon.service;

import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;

public interface InnerUserInterfaceService {

    /**
     * 通过accessKey查看是否已分配用户
     * @param accessKey accessKey
     * @return 用户接口关系
     */
    UserInterface getInvokeUserInterface(String accessKey);

    /**
     * 调用接口统计
     *
     * @param interfaceId 接口ID
     * @param userId          用户ID
     * @return boolean 是否执行成功
     */
    boolean subCallNum(long interfaceId, long userId);

    /**
     * 是否还有调用次数，其实就是在查对应接口用户关系表
     *
     * @param interfaceId 接口id
     * @param userId      用户id
     * @return UserInterfaceInfo 用户接口信息
     */
    boolean hasLeftNum(Long interfaceId, Long userId);


}
