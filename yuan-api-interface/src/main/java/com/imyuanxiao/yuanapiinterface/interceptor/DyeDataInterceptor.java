package com.imyuanxiao.yuanapiinterface.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 检查请求是否染色
 */
@Component
public class DyeDataInterceptor implements HandlerInterceptor {

    private static final String DYE_DATA_HEADER = "X-Dye-Data";
    private static final String DYE_DATA_VALUE = "reflux";

    /**
     * 调用接口时，如果发现是未经过网关染色的请求，不予响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求头中的染色数据
        String dyeData = request.getHeader(DYE_DATA_HEADER);

        if (dyeData == null || !dyeData.equals(DYE_DATA_VALUE)) {
            // 如果染色数据不存在或者不匹配，则返回错误响应
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }


        // 继续向下执行
        return true;
    }

}
