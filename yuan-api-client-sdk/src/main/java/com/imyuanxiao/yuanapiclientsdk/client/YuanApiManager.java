package com.imyuanxiao.yuanapiclientsdk.client;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.imyuanxiao.yuanapiclientsdk.utils.HeaderConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.imyuanxiao.yuanapiclientsdk.utils.SignUtil.genSign;

@Slf4j
@Data
public class YuanApiManager {

    private static String GATEWAY_HOST = "http://localhost:8091";

    /**
     * 生成新的请求头，里面包含accessKey，并且为了安全，使用签名密钥
     * @param body 调用接口所需要的原参数
     * @param method 调用接口所需要的method
     * @return 新的请求头
     */
    private Map<String, String> getHeaderMap(String accessKey, String secretKey, long id, String body, String method, String path, String url) {
        Map<String, String> hashMap = new HashMap<>();

        hashMap.put(HeaderConstant.ACCESSKEY, accessKey);
        // 接口ID
        hashMap.put(HeaderConstant.INTERFACE_ID, String.valueOf(id));
        // 只能一次请求只能调用一次接口，用后请求作废
        // hashMap.put(HeaderConstant.NONCE, RandomUtil.randomNumbers(4));
        // 请求一定时间内有效
        hashMap.put(HeaderConstant.TIMESTAMP, String.valueOf(System.currentTimeMillis() / 1000));
        // 签名
        hashMap.put(HeaderConstant.SIGN, genSign(body, secretKey));
        // 处理参数中文问题
        body = URLUtil.encode(body, CharsetUtil.CHARSET_UTF_8);
        hashMap.put(HeaderConstant.BODY, body);
        // 下面这三个是寻找调用接口的关键
        hashMap.put(HeaderConstant.METHOD, method);
        hashMap.put(HeaderConstant.URL,url);
        hashMap.put(HeaderConstant.PATH,path);
        return hashMap;
    }

    /**
     * 调用接口，把请求导向网关
     * @param params 接口参数
     * @param url 接口地址
     * @param method 接口使用方法
     * @return 接口调用结果
     */
    public String invokeInterface(String accessKey, String secretKey, long id, String method, String url, String path, String params)  {
        String result;
        log.info("SDK正在转发至GATEWAY_HOST:{}",GATEWAY_HOST);
        try(
                HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + path)
                        // 处理中文编码
                        .header("Accept-Charset", CharsetUtil.UTF_8)
                        .addHeaders(getHeaderMap(accessKey, secretKey, id,params, method,path,url))
                        .body(params)
                        .execute())
        {
            String body = httpResponse.body();
            result= JSONUtil.formatJsonStr(body);
        }
        log.info("SDK调用接口完成，响应数据：{}",result);
        return result;
    }

}
