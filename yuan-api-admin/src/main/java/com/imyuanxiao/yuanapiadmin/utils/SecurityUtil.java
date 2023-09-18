package com.imyuanxiao.yuanapiadmin.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.stereotype.Component;

/**
 * @description password encoder
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * @date: 2023/8/18 21:27
 **/
@Component
public class SecurityUtil {

    private static final String SECRET_KEY = "yuan_api";

    public String encodePassword(String password){
        return SecureUtil.md5(SECRET_KEY + password);
    }


    public boolean matches(String password, String passwordEncoded) {
        return encodePassword(password).equals(passwordEncoded);
    }
}
