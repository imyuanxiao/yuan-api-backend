package com.imyuanxiao.yuanapiadmin.model.param;

import com.imyuanxiao.yuanapiadmin.utils.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InterfaceRequestParam {

    /**
     * 0-add, 1-update, 2-
     */
    private Integer type;

    // @NotNull(message = "UserID is required.", groups = ValidationGroups.UpdateInterface.class)
    private Long id;

    @NotBlank(message = "Interface name is required.")
    private String name;

    @NotBlank(message = "Interface description is required.")
    private String description;

    @NotBlank(message = "Interface path is required.")
    private String path;

    @NotBlank(message = "Interface url is required.")
    private String url;

    private Object requestParam;

    private Object requestParamRemark;

    private Object responseParamRemark;

    private Object requestHeader;

    private Object responseHeader;

    private Integer status;

    @NotBlank(message = "Interface method is required.")
    private String method;

}
