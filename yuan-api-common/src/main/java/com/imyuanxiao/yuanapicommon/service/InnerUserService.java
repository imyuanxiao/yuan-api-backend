package com.imyuanxiao.yuanapicommon.service;

import com.imyuanxiao.yuanapicommon.model.entity.User;

public interface InnerUserService {

    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @param accessKey accessKey
     * @return User 用户信息
     */
    User getInvokeUser(String accessKey);

}
