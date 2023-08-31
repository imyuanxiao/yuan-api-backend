package com.imyuanxiao.yuanapiinterface.enums;

import lombok.Getter;

/**
 * @description result code
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Getter
public enum ResultCode {

    //操作成功
    SUCCESS(0, "操作成功"),

    // 参数校验相关
    PARAMS_ERROR(40000, "请求参数错误"),
    VALIDATE_FAILED(1006, "参数校验失败"),

    // 权限相关
    FORBIDDEN(1004, "没有相关权限"),
    UNAUTHORIZED_OPERATION(1005, "未授权的操作"),


    // 未找到资源
    INTERFACE_NOT_FOUND(1007, "接口不存在"),

    // 接口
    USER_INTERFACE_DISABLED(1030, "接口已被禁用"),
    USER_INTERFACE_NOT_APPLIED(1031, "接口未申请"),


    // 其他错误
    FAILED(2001, "操作失败"),
    ERROR(5000, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
