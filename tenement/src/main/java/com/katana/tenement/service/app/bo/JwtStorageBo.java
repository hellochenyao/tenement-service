package com.katana.tenement.service.app.bo;

import lombok.Data;

/**
 * Created by mumu on 2019/3/27.
 */
@Data
public class JwtStorageBo {

    private String sessionKey;
    private String openId;
    private int userId;

}
