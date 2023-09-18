package com.imyuanxiao.yuanapiadmin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.imyuanxiao.yuanapiadmin.annotation.AuthCheck;
import com.imyuanxiao.yuanapiadmin.model.param.InterfacePageParam;
import com.imyuanxiao.yuanapiadmin.model.param.InterfaceRequestParam;
import com.imyuanxiao.yuanapiadmin.model.param.InterfaceSetStatusParam;
import com.imyuanxiao.yuanapiadmin.model.vo.InterfacePageVO;
import com.imyuanxiao.yuanapiadmin.service.impl.InterfaceServiceImpl;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import com.imyuanxiao.yuanapicommon.exception.ApiException;
import com.imyuanxiao.yuanapicommon.model.entity.Interface;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.imyuanxiao.yuanapicommon.utils.CommonConst.ACTION_SUCCESSFUL;

/**
 * @description interface management
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 20:59
 **/

@Slf4j
@Validated
@RestController
@RequestMapping("/interface")
@Api(tags = "接口管理")
public class InterfaceController {

    @Autowired
    private InterfaceServiceImpl interfaceService;

    /**
     * 新增接口
     * @param param
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "增加接口")
    public String addInterface(@RequestBody InterfaceRequestParam param){
        interfaceService.addInterface(param);
        return ACTION_SUCCESSFUL;
    }

    /**
     * 删除接口
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除接口")
    @AuthCheck(mustRole = {"admin"})
    public String deleteInterface(@RequestBody Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        interfaceService.deleteInterface(ids);
        return ACTION_SUCCESSFUL;
    }

    /**
     * 修改接口
     * @param param
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改接口")
    @AuthCheck(mustRole = {"admin"})
    public String updateInterface(@RequestBody InterfaceRequestParam param){
        interfaceService.updateInterface(param);
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/setStatus")
    @ApiOperation(value = "上线/下线接口")
    @AuthCheck(mustRole = {"admin"})
    public String setInterfaceStatus(@RequestBody InterfaceSetStatusParam param){
        interfaceService.setInterfaceStatusById(param.getId(), param.getStatus());
        return ACTION_SUCCESSFUL;
    }

    /**
     * 分页获取接口信息
     * @param param
     * @return
     */
    @PostMapping("/page")
    public IPage<InterfacePageVO> pageInterface(@RequestBody InterfacePageParam param){
        return interfaceService.pageInterface(param);
    }

    /**
     * 根据ID获取接口详细信息
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "接口详细信息")
    public Interface getInterfaceById(@PathVariable("id") Long id){
        return interfaceService.getById(id);
    }

}
