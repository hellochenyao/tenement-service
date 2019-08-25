package com.katana.tenement.web.manager.api.adminUser;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by admin on 2018/5/24.
 */
@Data
public class ResponseLoginInfoGet {

    @ApiModelProperty("登陆返回管理员id")
    private Integer id;

    @ApiModelProperty("管理员账号")
    private String username;


    @ApiModelProperty("登陆返回jwt")
    private String jwt;

    @ApiModelProperty("登陆返回refreshToken")
    private String refreshToken;


}
