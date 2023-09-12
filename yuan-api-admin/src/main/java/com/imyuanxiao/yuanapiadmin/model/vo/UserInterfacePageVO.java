package com.imyuanxiao.yuanapiadmin.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserInterfacePageVO {

    private String id;

    private String name;
    private String description;

    private Integer leftNum;
    private Integer totalNum;

    private String accessKey;
    private String secretKey;

    private Integer status;


}
