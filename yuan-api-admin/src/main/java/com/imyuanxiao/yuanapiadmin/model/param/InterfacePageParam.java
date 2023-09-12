package com.imyuanxiao.yuanapiadmin.model.param;

import lombok.Data;

@Data
public class InterfacePageParam {
    private Integer current;
    private Integer pageSize;

    private String name;
    private String description;
}
