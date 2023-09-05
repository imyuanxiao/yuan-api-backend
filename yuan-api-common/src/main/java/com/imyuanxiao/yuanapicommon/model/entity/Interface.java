package com.imyuanxiao.yuanapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 接口信息表
 * @TableName interface
 */
@TableName(value ="interface")
@Data
@Accessors(chain = true)
public class Interface implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 接口地址
     */
    @TableField(value = "path")
    private String path;

    /**
     * 请求主机
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求参数
     */
    @TableField(value = "request_param")
    private Object requestParam;

    /**
     * 请求参数说明（名称、是否必填、类型、说明）
     */
    @TableField(value = "request_param_remark")
    private Object requestParamRemark;

    /**
     * 响应参数说明（名称、类型、说明）
     */
    @TableField(value = "response_param_remark")
    private Object responseParamRemark;

    /**
     * 请求头
     */
    @TableField(value = "request_header")
    private Object requestHeader;

    /**
     * 响应头
     */
    @TableField(value = "response_header")
    private Object responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 请求类型
     */
    @TableField(value = "method")
    private String method;

    /**
     * 创建人ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 逻辑删除（0-未删除，1-已删除）
     */
    //@TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(value = "updated_time")
    private Date updatedTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}