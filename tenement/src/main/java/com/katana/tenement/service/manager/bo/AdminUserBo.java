package com.katana.tenement.service.manager.bo;

import lombok.Data;

/**
 * Created by zuzu on 2019/04/30.
 */

@Data
public class AdminUserBo {
    //用户名
    private String username;

    //密码
    private String password;

    //账号状态
    private Integer status;

    //验证码
    private String authcode;

    //验证码uuid
    private String authcodeuuid;
}
