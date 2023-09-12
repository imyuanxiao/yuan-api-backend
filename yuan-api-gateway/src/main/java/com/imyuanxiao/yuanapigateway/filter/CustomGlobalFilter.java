package com.imyuanxiao.yuanapigateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.imyuanxiao.yuanapiclientsdk.utils.HeaderConstant;
import com.imyuanxiao.yuanapiclientsdk.utils.SignUtil;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import com.imyuanxiao.yuanapicommon.model.entity.UserInterface;
import com.imyuanxiao.yuanapicommon.model.vo.ResultVO;
import com.imyuanxiao.yuanapicommon.service.InnerUserInterfaceService;
import com.imyuanxiao.yuanapicommon.service.InnerInterfaceService;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Data
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 暂时只接受本地服务器前端传来的请求
     */
    public static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");
    private static final String DYE_DATA_HEADER = "X-Dye-Data";
    private static final String DYE_DATA_VALUE = "reflux";
    private static final String KEY_PREFFIX = "User_access:nonce:";

    @DubboReference
    InnerUserInterfaceService innerUserInterfaceService;

    @DubboReference
    InnerInterfaceService innerInterfaceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 在请求被路由之前执行的逻辑
        log.info("接口校验开始.......");

        // 1. 请求日志

        ServerHttpRequest request = exchange.getRequest();
        // 获取请求头和请求体
        HttpHeaders headers = request.getHeaders();
        // 请求来源地址
        String IP_ADDRESS = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        // 防止重放
        String nonce = headers.getFirst(HeaderConstant.NONCE);
        // 用户授权accessKey
        String accessKey = headers.getFirst(HeaderConstant.ACCESSKEY);
        // 用户授权secretKey结果SDK生成的sign
        String sign = headers.getFirst(HeaderConstant.SIGN);
        // 时间戳，只允许一定时间内的请求
        String timestamp = headers.getFirst(HeaderConstant.TIMESTAMP);
        // 请求接口所需的参数
        String body = URLUtil.decode(headers.getFirst(HeaderConstant.BODY), CharsetUtil.UTF_8);
        // 请求接口的ID
        String interfaceIdStr = headers.getFirst(HeaderConstant.INTERFACE_ID);
        // 请求接口的URL
        String interfaceUrl = headers.getFirst(HeaderConstant.URL);
        // 请求接口的路径
        String interfacePath = request.getPath().value();
        // 请求接口的方法
        String interfaceMethod = headers.getFirst(HeaderConstant.METHOD);
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求来源地址：{}", IP_ADDRESS);
        log.info("请求来源地址：{}", request.getRemoteAddress());
        log.info("请求方法：{}", request.getMethod());
        log.info("请求参数：{}", request.getQueryParams());
        log.info("接口方法：{}", interfaceMethod);
        log.info("接口地址：{}", interfaceUrl);
        log.info("接口路径：{}", interfacePath);

        // 请求参数不完整
        if (StrUtil.isEmpty(nonce)
                || StrUtil.isEmpty(sign)
                || StrUtil.isEmpty(timestamp)
                || StrUtil.isEmpty(interfaceMethod)
                || StrUtil.isEmpty(interfaceIdStr)
                || StrUtil.isEmpty(interfaceUrl)) {
            return handleException(exchange, new ApiException(ResultCode.FAILED, "请求头参数不完整！"));
        }

        // 2. 黑白名单，只有本机前端的请求能成功
        if(!IP_WHITE_LIST.contains(IP_ADDRESS)){
            return handleException(exchange, new ApiException(ResultCode.FAILED, "请求来源地址非法！"));
        }

        // 3. 时间戳 和 当前时间不能超过 5 分钟 (300000毫秒)
//        long currentTimeMillis = System.currentTimeMillis() / 1000;
//        long difference = currentTimeMillis - Long.parseLong(timestamp);
//        if (Math.abs(difference) > 300000) {
//            return handleException(exchange, new ApiException(ResultCode.FAILED, "请求超时！"));
//        }

        // todo 判断随机数是否存在，防止重放攻击，随机数放在redis内，使用一次即销毁
//        String key = KEY_PREFFIX + nonce;
//        String existNonce = (String) redisTemplate.opsForValue().get(key);
//        if (StrUtil.isNotBlank(existNonce)) {
//            throw new RuntimeException("请求重复！");
//        }

        // 4. 用户鉴权及接口校验
        // 4.1 通过 accessKey 查询是否存在该用户接口关系
        // 在该请求中会顺便检查用户是否存在，以及剩余调用次数
        UserInterface invokeUserInterface = null;
        try {
            invokeUserInterface = innerUserInterfaceService.getInvokeUserInterface(accessKey);
        } catch (Exception e) {
            // 处理返回的异常信息
            return handleException(exchange, e);
        }

        // 4.2 校验签名
        // 对比数据库中的 secretKey 生成的 sign 和前端传递的 sign
        String secretKey = invokeUserInterface.getSecretKey();
        log.info("网关得到SecretKey：{}", secretKey);
        String serverSign = SignUtil.genSign(body, secretKey);
//        if (!sign.equals(serverSign)) {
//            return handleException(exchange,  new ApiException(ResultCode.FAILED, "签名错误！"));
//        }
//
        Long interfaceId = Long.parseLong(interfaceIdStr);
        // 4.3 校验接口是否存在
        try {
            innerInterfaceService.getInterface(interfaceId, interfaceUrl, interfaceMethod, interfacePath);
        } catch (Exception e) {
            // 处理返回的异常信息
            return handleException(exchange, e);
        }

        log.info("接口校验完毕，准备转发请求！");
        Long userId = invokeUserInterface.getUserId();
        // 5. 请求转发，调用模拟接口
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // 在响应被发送到客户端之前执行的逻辑
                    ServerHttpResponse response = exchange.getResponse();
                    // 6. 响应日志
                    log.info("响应状态码：{}", response.getStatusCode());
                    // 7. 调用成功，接口调用次数 + 1
                    try {
                        innerUserInterfaceService.subCallNum(interfaceId, userId);
                    } catch (Exception e) {
                        log.error("处理调用次数时发生错误！");
                    }
                }));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 统一异常处理
     * @param exchange
     * @param e
     * @return
     */
    private Mono<Void> handleException(ServerWebExchange exchange, Exception e) {
        ServerHttpResponse response = exchange.getResponse();
        log.info("响应状态码：{}", response.getStatusCode());
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        // 错误信息
        ResultCode errorCode = ResultCode.ERROR;
        String errorMessage = e.getMessage();
        // 自定义错误特殊处理
        if (e instanceof ApiException apiException) {
            errorCode = apiException.getResultCode();
            errorMessage = apiException.getMsg();
        }
        log.info("错误信息：{}", errorMessage);
        String responseBody = JSONUtil.toJsonStr(new ResultVO<>(errorCode, errorMessage));
        return response
                .writeWith(Mono.just(response
                        .bufferFactory()
                        .wrap(responseBody.getBytes())));
    }

}
