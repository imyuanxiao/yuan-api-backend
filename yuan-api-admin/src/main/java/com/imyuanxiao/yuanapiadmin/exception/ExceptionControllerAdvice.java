package com.imyuanxiao.yuanapiadmin.exception;

import com.imyuanxiao.yuanapicommon.exception.CommonExceptionControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description  Global exception handler
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice extends CommonExceptionControllerAdvice {
}
