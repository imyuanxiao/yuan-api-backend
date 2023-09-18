package com.imyuanxiao.yuanapiinterface.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@Api(tags = "内部接口")
public class InnerApiController {

    @PostMapping("/api/randName")
    public String randName(@RequestBody Map<String, Integer> requestBody){
        Integer number = requestBody.get("number");
        String[] surnames = {
                "赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈",
                "楮", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
        };
        StringBuilder result = new StringBuilder(RandomUtil.randomEle(surnames));
        for(; number > 1; number--){
            result.append(RandomUtil.randomChinese());
        }
        return result.toString();
    }

    @GetMapping("/api/getName")
    public String getUsername(String name) {
        System.out.println(name);
        return "你的名字是：" + name;
    }

    @GetMapping("/api/exception")
    public void testExceprion() {
        throw new ApiException(ResultCode.FAILED);
    }

}
