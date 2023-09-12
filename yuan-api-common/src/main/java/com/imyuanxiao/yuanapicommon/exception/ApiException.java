package com.imyuanxiao.yuanapicommon.exception;

import com.imyuanxiao.yuanapicommon.enums.ResultCode;
import lombok.Data;

/**
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 */
@Data
public class ApiException extends RuntimeException{

    private ResultCode resultCode;
    private String msg;

    public ApiException(ResultCode resultCode, String msg) {
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public ApiException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
        this.msg = resultCode.getMsg();
    }

}
