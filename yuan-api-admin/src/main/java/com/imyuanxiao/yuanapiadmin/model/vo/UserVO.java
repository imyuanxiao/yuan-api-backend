package com.imyuanxiao.yuanapiadmin.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

/**
 * @description  User Value Object
 * @author  imyuanxiao
 **/
@Data
@Accessors(chain = true)
public class UserVO {

    private Long id;
    private String username;
    private String role;
    private Integer status;
    private String phone;
    private String email;
    private String avatar;
    private String nickName;

}
