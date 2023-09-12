package com.imyuanxiao.yuanapiadmin.model.vo;

import lombok.Data;

import java.sql.Date;

/**
 * @description  接口分页信息
 * @author  imyuanxiao
 **/
@Data
public class InterfacePageVO {

    private String id;
    private String name;
    private String description;
    private String method;
    private Integer status;
    private Date createdTime;

}
