package com.imyuanxiao.yuanapiadmin.model.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserProfileParam {
    @NotNull
    private Long id;
    private String username;
    private String nickName;
    private String phone;
    private String email;
}
