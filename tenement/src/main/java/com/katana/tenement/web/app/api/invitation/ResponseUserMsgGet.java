package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

@Data
public class ResponseUserMsgGet {


    private Integer id;

    //发消息者id
    private int userId;

    //发消息者昵称
    private String nickname;

    //回复对象id
    private int answerUserId;

    //回复对象名称
    private String answerUserNickname;

    //回复消息id
    private int pid;

    //帖子
    private int invitationId;

    //留言时间
    private String createTime;

    //留言
    private String msg;
}
