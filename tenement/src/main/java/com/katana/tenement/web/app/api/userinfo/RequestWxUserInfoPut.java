package com.katana.tenement.web.app.api.userinfo;

import lombok.Data;

/**
 * Created by mumu on 2019/4/11.
 */
@Data
public class RequestWxUserInfoPut {

    private String encryptedData;

    private String iv;
}
