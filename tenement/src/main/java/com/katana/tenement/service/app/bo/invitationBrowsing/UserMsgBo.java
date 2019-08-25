package com.katana.tenement.service.app.bo.invitationBrowsing;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserMsgBo {

    //发消息者id
    private int userId;

    //帖子
    private int invitationId;

    private String msg;

    private int  answerMsgId;

    private int responseUserId;
}
