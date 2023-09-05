package com.imyuanxiao.yuanapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户_接口关系表
 * @TableName user_interface
 */
@TableName(value ="user_interface")
@Data
@Accessors(chain = true)
public class UserInterface implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 接口ID
     */
    @TableField(value = "interface_id")
    private Long interfaceId;

    /**
     * 访问凭证
     */
    @TableField(value = "access_key")
    private String accessKey;

    /**
     * 密钥
     */
    @TableField(value = "secret_key")
    private String secretKey;

    /**
     * 剩余调用次数
     */
    @TableField(value = "left_num")
    private Integer leftNum;

    /**
     * 总调用次数
     */
    @TableField(value = "total_num")
    private Integer totalNum;

    /**
     * 接口状态（0-正常，1-禁用）
     */
    @TableField(value = "status")
    private Integer status;


    /**
     * 逻辑删除（0-未删除，1-已删除）
     */
    //@TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}