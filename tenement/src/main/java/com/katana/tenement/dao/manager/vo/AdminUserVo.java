package com.katana.tenement.dao.manager.vo;

import lombok.Data;

/**
 * Created by zuzu on 2019/04/30.
 */
@Data
public class AdminUserVo {

    //用户名
    private String username;

    //密码
    private String password;

    //账号状态
    private Integer status;

    //验证码
    private String authcode;
}
