package com.imyuanxiao.yuanapiadmin.model.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserPasswordParam {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
