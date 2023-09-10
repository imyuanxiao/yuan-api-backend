package com.imyuanxiao.yuanapiinterface.controller;

import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/inner")
@Api(tags = "内部接口")
public class InnerApiController {

    @GetMapping("/getName")
    public String getUsername(String name) {
        System.out.println(name);
        return "你的名字是：" + name;
    }

    @GetMapping("/exception")
    public void testExceprion() {
        throw new ApiException(ResultCode.FAILED);
    }

}
