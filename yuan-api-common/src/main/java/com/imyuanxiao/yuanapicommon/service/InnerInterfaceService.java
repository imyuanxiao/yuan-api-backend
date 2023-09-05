package com.imyuanxiao.yuanapicommon.service;

import com.imyuanxiao.yuanapicommon.model.entity.Interface;

public interface InnerInterfaceService {
    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     *
     * @return Interface 接口信息
     */
    Interface getInterface(long id, String url, String method, String path);
}
