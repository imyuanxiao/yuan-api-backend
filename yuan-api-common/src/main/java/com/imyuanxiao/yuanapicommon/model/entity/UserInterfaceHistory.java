package com.imyuanxiao.yuanapicommon.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户_接口调用历史表
 * @TableName user_interface_history
 */
@TableName(value ="user_interface_history")
@Data
@Accessors(chain = true)
public class UserInterfaceHistory implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id")
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
     * 调用结果（0-成功，1-失败）
     */
    @TableField(value = "result")
    private Integer result;

    /**
     * 逻辑删除（0-未删除，1-已删除）
     */
    //@TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}