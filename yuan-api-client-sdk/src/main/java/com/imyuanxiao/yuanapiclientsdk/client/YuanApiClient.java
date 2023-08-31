package com.imyuanxiao.yuanapiclientsdk.client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class YuanApiClient {


    private String accessKey;

    private String secretKey;

    public YuanApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

}
