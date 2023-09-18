package com.imyuanxiao.yuanapicommon.model.vo;

import com.imyuanxiao.yuanapicommon.annotation.ExceptionCode;
import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import lombok.Getter;

/**
 * @description  Customize unified response body. 和umiJs最佳实践统一
 * @author  imyuanxiao
 **/
@Getter
public class ResultVO<T> {

    private boolean success;

    private int code;

    private String message;

    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
        this.success = true;
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
        this.data = data;
    }

    public ResultVO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultVO(ExceptionCode annotation, T data) {
        this.code = annotation.value();
        this.message = annotation.message();
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d,\"msg\":\"%s\",\"data\":\"%s\"}", code, message, data.toString());
    }
}
