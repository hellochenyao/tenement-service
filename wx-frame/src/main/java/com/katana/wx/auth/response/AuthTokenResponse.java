package com.katana.wx.auth.response;

import lombok.Data;

/**
 * 授权token的返回值
 *
 * @author katana
 */
@Data
public class AuthTokenResponse implements java.io.Serializable {
    private String access_token; // 范围授权token
    private int expires_in; // 过时时间
    private String refresh_token;// 刷新token
    private String openid; // 用户的openid
    private String scope; // 范围
    private String unionid;

    public AuthTokenResponse() {
    }

    public AuthTokenResponse(String access_token, int expires_in,
                             String refresh_token, String openid, String scope, String unionid) {
        super();
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.openid = openid;
        this.scope = scope;
        this.unionid = unionid;
    }
}