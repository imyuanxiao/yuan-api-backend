package com.imyuanxiao.yuanapiadmin.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;

public class KeyUtil {

    public static String generateAccessKey() {
        return RandomUtil.randomString(32);
    }

    public static String generateSecretKey() {
        return SecureUtil.md5(RandomUtil.randomString(32));
    }

}
