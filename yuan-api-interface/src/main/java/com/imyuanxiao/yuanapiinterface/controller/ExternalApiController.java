package com.imyuanxiao.yuanapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "外部接口")
public class ExternalApiController {

    private static String invokeExternalApi(String url, String body) {
        try(HttpResponse httpResponse = HttpRequest.get(url + "?" + body)
                .execute()){
            return httpResponse.body();
        }
    }

    /**
     * 毒鸡汤
     */
    @PostMapping("/api/dujitang")
    public String randDuJiTang(HttpServletRequest request) {
        String url = "http://api.btstu.cn/yan/api.php";
        String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
        return invokeExternalApi(url, body);
    }

    /**
     * 土味情话
     */
    @PostMapping("/api/qinghua")
    public String randQingHua(HttpServletRequest request) {
            String url = "https://api.uomg.com/api/rand.qinghua";
            String body = URLUtil.decode(request.getHeader("body"), CharsetUtil.CHARSET_UTF_8);
            return invokeExternalApi(url, body);
    }

}

