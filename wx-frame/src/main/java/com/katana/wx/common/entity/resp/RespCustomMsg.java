package com.katana.wx.common.entity.resp;

/**
 * 回复客服消息
 *
 * @author katana
 */
public class RespCustomMsg extends RespBasicMsg {

    @Override
    public String setMsgType() {
        return "transfer_customer_service";
    }


}
