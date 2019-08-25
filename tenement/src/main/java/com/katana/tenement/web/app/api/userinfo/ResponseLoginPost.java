package com.katana.tenement.web.app.api.userinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseLoginPost {

    @ApiModelProperty("登录时生成jwt,有效期30分钟")
    private String jwt;

    @ApiModelProperty("登录时生成refreshToken")
    private String refreshToken;

    @ApiModelProperty("用户id")
    private int userId;

    @ApiModelProperty("是否首次登陆")
    private int isNew;
}
