package com.katana.tenement.service.app.bo.privateMsg;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateMsgBo {

    private String content;

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private int isRead;

    private int type;

    private String descText;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
