package com.cav.clairvoyance.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRespVO {
    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户id")
    private String id;


}
