package com.imyuanxiao.yuanapiinterface.controller;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.URLUtil;
import io.swagger.annotations.Api;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/inner")
@Api(tags = "内部接口")
public class InnerApiController {

    @GetMapping("/getName")
    public String getUsername(String name, HttpServletRequest request) {
        System.out.println(name);
        return "你的名字是：" + name;
    }

}
