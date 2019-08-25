package com.katana.tenement.web.app.api.userinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseJwtAndRefreshPost {

    @ApiModelProperty("登录时生成jwt,有效期30分钟")
    private String jwt;

    @ApiModelProperty("登录时生成refreshToken")
    private String refreshToken;
}
