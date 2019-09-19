package com.katana.tenement.web.app.api.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgWSDto {
    private String content;

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private String receiveName;

    private String receiveAvatar;

    private int isRead;

    private int type;

    private String descText;

    private String createTime;

}
