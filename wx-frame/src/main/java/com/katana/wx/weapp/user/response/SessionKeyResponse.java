package com.katana.wx.weapp.user.response;


import lombok.Data;

import java.io.Serializable;

/**
 * @author katana
 * @date 2018/1/19
 */
@Data
public class SessionKeyResponse implements Serializable {

    private String openid;

    private String session_key;

    private String unionid;

}
