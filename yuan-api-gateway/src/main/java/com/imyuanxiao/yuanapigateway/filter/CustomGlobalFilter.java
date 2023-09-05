package com.imyuanxiao.yuanapigateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.imyuanxiao.yuanapiclientsdk.utils.HeaderConstant;
import com.imyuanxiao.yuanapiclientsdk.utils.SignUtil;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@Data
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 暂时只接受本地服务器前端传来的请求
     */
    public static final List<String> IP_WHITE_LIST = Collections.singletonList("");

    private static final String DYE_DATA_HEADER = "X-Dye-Data";
    private static final String DYE_DATA_VALUE = "reflux";
    private static final String KEY_PREFFIX = "User_access:nonce:";



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 在请求被路由之前执行的逻辑
        log.info("接口校验开始.......");

        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String IP_ADDRESS = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", request.getPath().value());
        log.info("请求方法：{}", request.getMethod());
        log.info("请求参数：{}", request.getQueryParams());
        log.info("请求来源地址：{}", IP_ADDRESS);//本机IP才有效
        log.info("请求来源地址：{}", request.getRemoteAddress());//本机IP才有效

        // 2. 黑白名单，暂时只有本机前端的请求能成功
        if(!IP_WHITE_LIST.contains(IP_ADDRESS)){
            // 通过处理状态码返回响应，此响应无参数
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
            // todo throw new APIException();
        }

        // 3. 用户鉴权 （判断 accessKey 和 secretKey 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst(HeaderConstant.ACCESSKEY);
        String timestamp = headers.getFirst(HeaderConstant.TIMESTAMP);
        String nonce = headers.getFirst(HeaderConstant.NONCE);
        String sign = headers.getFirst(HeaderConstant.SIGN);
        String body = URLUtil.decode(headers.getFirst(HeaderConstant.BODY), CharsetUtil.UTF_8);
        String id = headers.getFirst(HeaderConstant.API_ID);
        String URL = headers.getFirst(HeaderConstant.URL);
        String method = headers.getFirst(HeaderConstant.METHOD);
        if (StrUtil.isEmpty(nonce)
                || StrUtil.isEmpty(sign)
                || StrUtil.isEmpty(timestamp)
                || StrUtil.isEmpty(method)
                || StrUtil.isEmpty(id)
                || StrUtil.isEmpty(URL)) {
            throw new RuntimeException("请求头参数不完整！");
            //throw new APIException(ResultCode.FORBIDDEN_ERROR, "请求头参数不完整！");
        }



//        long InterfaceInfoId = Integer.valueOf(id).longValue();
//         //通过 accessKey 查询是否存在该用户
//         User invokeUser = innerUserService.getInvokeUser(accessKey);
//        if (invokeUser == null) {
//            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "accessKey 不合法！");
//        }

        // 判断随机数是否存在，防止重放攻击
//        String key = KEY_PREFFIX + nonce;
//        String existNonce = (String) redisTemplate.opsForValue().get(key);
//        if (StrUtil.isNotBlank(existNonce)) {
//            throw new RuntimeException("请求重复！");
//        }

        // 时间戳 和 当前时间不能超过 5 分钟 (300000毫秒)
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        long difference = currentTimeMillis - Long.parseLong(timestamp);
        if (Math.abs(difference) > 300000) {
            throw new RuntimeException("请求超时！");
        }

        // 校验签名
        // 应该通过 accessKey 查询数据库中的 secretKey 生成 sign 和前端传递的 sign 对比
        // TODO String secretKey = invokeUser.getSecretKey();
        String secretKey = "123";
        //log.info("网关得到tSecretKey：{}", secretKey);
        String serverSign = SignUtil.genSign(body, secretKey);
        if (!sign.equals(serverSign)) {
            throw new RuntimeException("签名错误！");
        }

        // 4. 检查请求的模拟接口是否存在
        

        // 5. 请求转发，调用模拟接口
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    // 在响应被发送到客户端之前执行的逻辑
                    ServerHttpResponse response = exchange.getResponse();

                    // 6. 响应日志
                    log.info("响应状态码：{}", response.getStatusCode());

                }));


        // 4. 请求的模拟接口是否存在？
        // 从数据库中查询接口是否存在，以及方法是否匹配（还有请求参数是否正确）
       /* InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(InterfaceInfoId, URL, method, path);
        } catch (Exception e) {
            log.error("getInvokeInterface error", e);
        }
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口不存在！");
        }
        Long interfaceInfoId = interfaceInfo.getId();
        Long userId = invokeUser.getId();
        // 查询剩余调用次数
        UserInterfaceInfo userInterfaceInfo = innerUserInterfaceInfoService.hasLeftNum(interfaceInfoId, userId);
        // 接口未绑定用户，这里是不能完全信任前端，有可能客户未开通接口也调用？或者是管理员不开通直接调用？
        if (userInterfaceInfo == null) {
            Boolean save = innerUserInterfaceInfoService.addDefaultUserInterfaceInfo(interfaceInfoId, userId);
            if (save == null || !save) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口绑定用户失败！");
            }
        }
        if (userInterfaceInfo != null && userInterfaceInfo.getLeftNum() <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用次数已用完！");
        }
        log.info("接口校验完毕，准备转发请求！");
        // 5. 请求转发，调用模拟接口
        // 5.1 去除无用请求头，不知道能不能提高一点速度.使用headers.remove会直接跑不通
        // 6. 响应日志
        return handleResponse(exchange, chain, interfaceInfoId, userId);*/


    }

    @Override
    public int getOrder() {
        return 0;
    }

// s

//    /**
//     * 处理响应
//     */
//    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
//        try {
//            ServerHttpResponse originalResponse = exchange.getResponse();
//            HttpStatus statusCode = originalResponse.getStatusCode();
//            // 这个好像是网关处理的状态码？...
//            if (statusCode == HttpStatus.OK) {
//                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
//                    @Override
//                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
//                        if (body instanceof Flux) {
//                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
//                            return super.writeWith(
//                                    fluxBody.map(dataBuffer -> {
//                                        // 7. 调用成功，接口调用次数 + 1 invokeCount，拿到调用成功的 请求头和返回头
//                                        ServerHttpRequest invokeRequest = exchange.getRequest();
//                                        ServerHttpResponse invokeResponse = exchange.getResponse();
//                                        try {
//                                            postHandler(invokeRequest, invokeResponse, interfaceInfoId, userId);
//                                        } catch (Exception e) {
//                                            log.error("invokeCount error", e);
//                                        }
//                                        byte[] orgContent = new byte[dataBuffer.readableByteCount()];
//                                        dataBuffer.read(orgContent);
//                                        DataBufferUtils.release(dataBuffer);//释放掉内存
//                                        // 构建日志
//                                        String data = new String(orgContent, StandardCharsets.UTF_8); //data
//                                        log.info("原始响应结果：" + data);
//                                        if (invokeResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
//                                            data = String.format("{\"code\": %d,\"msg\":\"%s\",\"data\":\"%s\"}",
//                                                    ErrorCode.NOT_FOUND_ERROR.getCode(), "接口请求路径不存在", "null");
//                                            log.info("处理404后响应结果：" + data);
//                                        }
//                                        DataBufferFactory bufferFactory = invokeResponse.bufferFactory();
//                                        // log.info("date长度：{}",data.length());
//                                        // 打印日志
//                                        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
//                                        // 告知客户端Body的长度，如果不设置的话客户端会一直处于等待状态不结束
//                                        HttpHeaders headers = invokeResponse.getHeaders();
//                                        headers.setContentLength(bytes.length);
//                                        return bufferFactory.wrap(bytes);
//                                    })
//                            );
//                        } else {
//                            // 8. 调用失败，返回规范的错误码
//                            log.error("<--- {} 响应code异常", getStatusCode());
//                        }
//                        return super.writeWith(body);
//                    }
//                };
//
//                // 流量染色，只有染色数据才能被调用
//                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                        .header(DYE_DATA_HEADER, DYE_DATA_VALUE)
//                        .build();
//
//                ServerWebExchange serverWebExchange = exchange.mutate()
//                        .request(modifiedRequest)
//                        .response(decoratedResponse)
//                        .build();
//                return chain.filter(serverWebExchange);
//            }
//            //降级处理返回数据
//            return chain.filter(exchange);
//        } catch (Exception e) {
//            log.error("网关处理异常响应.\n" + e);
//            return chain.filter(exchange);
//        }
//    }*/

//    private void postHandler(ServerHttpRequest request, ServerHttpResponse response, Long interfaceInfoId, Long userId) {
//        RLock lock = redissonClient.getLock("api:add_interface_num");
//        if (response.getStatusCode() == HttpStatus.OK) {
//            CompletableFuture.runAsync(() -> {
//                if (lock.tryLock()) {
//                    try {
//                        addInterfaceNum(request, interfaceInfoId, userId);
//                    } finally {
//                        lock.unlock();
//                    }
//                }
//            });
//        }
//    }

//    private void addInterfaceNum(ServerHttpRequest request, Long interfaceInfoId, Long userId) {
//        // 使用分布式锁实现接口总调用次数的增加
//        // 判断nonce是否为空和判断请求剩余次数应该在转发调用前做
//        String nonce = request.getHeaders().getFirst(HeaderConstant.NONCE);
//        String key = KEY_PREFFIX + nonce;
//        redisTemplate.opsForValue().set(key, 1, 5, TimeUnit.MINUTES);
//        // 外面包了一层分布式锁，invokeCount应该不需要上分布式事务，因为只有一人会进行invokeCount的操作。
//        innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
//    }

}
