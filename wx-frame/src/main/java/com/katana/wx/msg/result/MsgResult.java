package com.katana.wx.msg.result;

import lombok.Data;

/**
 * 消息 返回的结果数
 *
 * @author katana
 */
@Data
public class MsgResult {
    private int errcode; // 错误码
    private String errmsg; // 错误信息
    private String msg_id; // 消息id
    private String template_id; // 模板消息id
    private String msgid;        //消息id(发送模板消息)


    @Override
    public String toString() {
        return "MsgResult [errcode=" + errcode + ", errmsg=" + errmsg
                + ", msg_id=" + msg_id + ", msgid=" + msgid + ", template_id="
                + template_id + "]";
    }
}
