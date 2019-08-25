package com.katana.tenement.web.manager.api.adminUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by admin on 2018/5/24.
 */
@Data
public class RequestLoginInfoGet {

    @ApiModelProperty("登录名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String authcode;

    @ApiModelProperty("验证码UUID")
    private String authcodeuuid;
}