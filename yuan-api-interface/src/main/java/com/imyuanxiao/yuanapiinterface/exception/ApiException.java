package com.imyuanxiao.yuanapiinterface.exception;

import com.imyuanxiao.yuanapiinterface.enums.ResultCode;
import lombok.Getter;

/**
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 */
@Getter
public class ApiException extends RuntimeException{

    private ResultCode resultCode;
    private String msg;

    public ApiException(ResultCode resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ApiException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.msg = resultCode.getMsg();
    }

}
