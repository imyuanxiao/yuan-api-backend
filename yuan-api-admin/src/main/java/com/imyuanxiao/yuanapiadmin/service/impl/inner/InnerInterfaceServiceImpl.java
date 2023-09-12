package com.imyuanxiao.yuanapiadmin.service.impl.inner;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.yuanapiadmin.mapper.InterfaceMapper;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import com.imyuanxiao.yuanapicommon.service.InnerInterfaceService;
import com.imyuanxiao.yuanapicommon.utils.CommonConst;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【interface(接口信息表)】的数据库操作Service实现
* @createDate 2023-08-18 21:13:14
*/
@DubboService
public class InnerInterfaceServiceImpl extends ServiceImpl<InterfaceMapper, Interface>
    implements InnerInterfaceService {

    @Override
    public Interface getInterface(Long id, String url, String method, String path) {
        try {
            Interface targetInterface = this.lambdaQuery()
                    .eq(Interface::getUrl, url)
                    .eq(Interface::getId, id)
                    .eq(Interface::getMethod, method)
                    .eq(Interface::getPath, path.replaceFirst("/api", "")).one();
            // 接口是否存在或异常
            if(targetInterface == null || targetInterface.getStatus().equals(CommonConst.INTERFACE_STATUS_OFFLINE)){
                throw new ApiException(ResultCode.FAILED, "接口不存在或已关闭！");
            }
            return targetInterface;
        } catch (ApiException e) {
            throw e; // 如果是自定义的 ApiException，直接抛出
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED);
        }
    }
}




