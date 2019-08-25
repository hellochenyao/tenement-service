package com.katana.tenement.web.manager.api.adminUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseAuthCodeGet {
    @ApiModelProperty("base64格式的验证码")
    private String authCode;
    @ApiModelProperty("唯一的uuid")
    private String uuid;
}
