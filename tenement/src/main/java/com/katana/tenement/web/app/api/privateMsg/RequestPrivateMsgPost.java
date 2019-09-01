package com.katana.tenement.web.app.api.privateMsg;

import lombok.Data;

@Data
public class RequestPrivateMsgPost {

    private String content;

    //接收方用户id
    private Integer receiveUserid;

    private int msgId;//消息id

    private String type;

    private String descText;

}
