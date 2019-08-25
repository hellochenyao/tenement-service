package com.katana.wx.jssdk.response;


import com.katana.wx.common.conn.Connection;

/**
 * @author katana
 * @date 2017/5/18
 */

public class JsapiTicketResponse extends Connection {


    private int errcode;
    private String errmsg;

    /**
     * 公众号用于调用微信JS接口的临时票据
     */
    private String ticket;

    /**
     * 过时时间
     */
    private int expires_in;


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
