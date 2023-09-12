package com.imyuanxiao.yuanapiadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.vo.UserInterfacePageVO;
import com.imyuanxiao.yuanapiadmin.service.impl.UserInterfaceServiceImpl;
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
     * 改变调用次数
     * @param id
     * @param type 0-加500次 1-减1次
     * @return
     */
    @GetMapping("/setCallNum/{type}/{id}")
    @ApiOperation(value = "设置接口调用次数")
    public String setCallNum(@PathVariable("type") Integer type, @PathVariable("id") Long id){
        return userInterfaceService.setCallNum(id, type);
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
     * 根据request的accessKey和secretKey判断用户是否有权调用接口
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/checkAuth/{id}")
    public boolean checkAuth(@PathVariable("id") Long id, HttpServletRequest request){
        String accessKey = request.getAttribute("accessKey").toString();
        String secretKey = request.getAttribute("secretKey").toString();
        return userInterfaceService.checkAuth(id, accessKey, secretKey);
    }

}
