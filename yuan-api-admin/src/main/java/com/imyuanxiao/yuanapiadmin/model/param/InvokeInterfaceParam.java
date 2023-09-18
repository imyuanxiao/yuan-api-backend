package com.imyuanxiao.yuanapiadmin.model.param;

import lombok.Data;

@Data
public class InvokeInterfaceParam {
    private String accessKey;
    private String secretKey;
    private Long id;
    private String method;
    private String url;
    private String path;
    private String requestParams;
}
