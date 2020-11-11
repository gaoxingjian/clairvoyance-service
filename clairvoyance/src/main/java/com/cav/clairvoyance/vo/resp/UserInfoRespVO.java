package com.cav.clairvoyance.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoRespVO {
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "邮箱")
    private String email;
}
