package com.imyuanxiao.yuanapiadmin.model.param;

import com.alibaba.nacos.shaded.javax.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InterfaceRequestParam {

    private Long id;

    @NotBlank(message = "Interface name is required.")
    private String name;

    @NotBlank(message = "Interface description is required.")
    private String description;

    @NotBlank(message = "Interface method is required.")
    private String method;

    @NotBlank(message = "Interface url is required.")
    private String url;

    @NotBlank(message = "Interface path is required.")
    private String path;

    private Object requestParam;

    private Object requestParamRemark;

    private Object responseParamRemark;

    private Object requestHeader;

    private Object responseHeader;

}
