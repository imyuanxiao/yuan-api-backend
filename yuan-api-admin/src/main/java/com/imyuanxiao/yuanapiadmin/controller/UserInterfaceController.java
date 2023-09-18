package com.imyuanxiao.yuanapiadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.param.InvokeInterfaceParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserInterfacePageVO;
import com.imyuanxiao.yuanapiadmin.service.impl.UserInterfaceServiceImpl;
import com.imyuanxiao.yuanapiclientsdk.client.YuanApiManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/userInterface")
@Api(tags = "用户接口关系管理")
public class UserInterfaceController {

    @Autowired
    private UserInterfaceServiceImpl userInterfaceService;

    /**
     * 申请接口，默认给予500次调用次数，
     * @param id
     * @return
     */
    @GetMapping("/apply/{id}")
    @ApiOperation(value = "申请接口")
    public String apply(@PathVariable("id") Long id){
        return userInterfaceService.apply(id);
    }

    /**
     * 分页获取已申请接口信息
     * @param param
     * @return
     */
    @PostMapping("/page")
    public IPage<UserInterfacePageVO> pageUserInterface(@RequestBody InterfacePageParam param){
        return userInterfaceService.pageUserInterface(param);
    }

    /**
     * 在线调用接口
     * @param param
     * @return
     */
    @PostMapping("/invokeInterface")
    public String onlineInvokeInterface(@RequestBody InvokeInterfaceParam param){
        return new YuanApiManager().invokeInterface(
                param.getAccessKey(),
                param.getSecretKey(),
                param.getId(),
                param.getMethod(),
                param.getUrl(),
                param.getPath(),
                param.getRequestParams());
    }

}
