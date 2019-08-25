package com.katana.wx.common.response;


import com.katana.wx.common.conn.Connection;


/**
 * @author katana
 * @date 2017/5/18
 */

public class AccessTokenResponse extends Connection {

    /**
     * 范围授权token
     */
    private String access_token;

    /**
     * 过时时间
     */
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
