package com.imyuanxiao.yuanapiclientsdk.utils;

/**
 * header常量
 */
public class HeaderConstant {
    /**
     * 用户accessKey
     */
    public static final String ACCESSKEY = "accessKey";
    /**
     * 调用接口的id
     */
    public static final String API_ID = "id";
    /**
     * 随机数，使用后失效，保证接口只能使用一次
     */
    public static final String NONCE = "nonce";
    /**
     * 时间戳，保证是一定时间内的请求
     */
    public static final String TIMESTAMP = "timestamp";
    /**
     * 签名，对比看看有没有资格
     */
    public static final String SIGN ="sign";
    /**
     * 参数列表
     */
    public static final String BODY="body";
    //对于外部的接口，采用下面的方式进行判断
    /**
     * 调用方式
     */
    public static final String METHOD="method";
    /**
     * host
     */
    public static final String PATH="path";
    /**
     * url
     */
    public static final String URL="url";
}
