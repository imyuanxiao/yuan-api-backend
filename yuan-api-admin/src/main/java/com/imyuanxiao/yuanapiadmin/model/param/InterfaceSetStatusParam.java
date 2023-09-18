package com.imyuanxiao.yuanapiadmin.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InterfaceSetStatusParam {
    @NotBlank
    private Long id;
    @NotBlank
    private Integer status;
}
