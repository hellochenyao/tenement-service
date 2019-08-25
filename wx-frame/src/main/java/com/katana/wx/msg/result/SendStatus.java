package com.katana.wx.msg.result;

import lombok.Data;

/**
 * 发送的状态
 *
 * @author katana
 */
@Data
public class SendStatus {
    private long msg_id; // 消息的id
    private String msg_status; // 消息的状态

}
