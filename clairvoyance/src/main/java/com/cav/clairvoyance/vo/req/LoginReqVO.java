package com.cav.clairvoyance.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReqVO {

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
