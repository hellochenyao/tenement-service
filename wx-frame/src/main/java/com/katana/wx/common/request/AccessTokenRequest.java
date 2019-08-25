package com.katana.wx.common.request;


/**
 * @author katana
 * @date 2017/5/18
 */
public class AccessTokenRequest {

    private String key;

    private String secret;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
