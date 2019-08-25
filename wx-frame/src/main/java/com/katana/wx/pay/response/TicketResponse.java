package com.katana.wx.pay.response;

import lombok.Data;

/**
 * 网页中获取getTicket中的链接
 *
 * @author katana
 */
@Data
public class TicketResponse implements java.io.Serializable {
    private int errcode;
    private String errmsg;
    private String ticket;
    private int expires_in;

    public TicketResponse() {
        super();
    }

    public TicketResponse(int errcode, String errmsg, String ticket,
                          int expires_in) {
        super();
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.ticket = ticket;
        this.expires_in = expires_in;
    }

}
