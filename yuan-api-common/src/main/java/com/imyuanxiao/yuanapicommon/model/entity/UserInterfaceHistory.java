package com.imyuanxiao.yuanapicommon.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

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

    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}