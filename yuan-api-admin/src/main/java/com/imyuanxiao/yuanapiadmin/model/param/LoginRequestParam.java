package com.imyuanxiao.yuanapiadmin.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @description  Receive login-related parameters.
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class LoginRequestParam {

    @NotBlank(message = "Username is required.")
    @Length(min = 4, max = 20, message = "Username must be between 4-20 characters in length.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Length(min = 4, max = 20, message = "Password must be between 4-20 characters in length.")
    private String password;

}
