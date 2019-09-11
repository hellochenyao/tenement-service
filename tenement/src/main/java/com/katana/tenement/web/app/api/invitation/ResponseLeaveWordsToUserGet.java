package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

import java.util.List;

/**
 * 帖子所有留言的回复
 * Created by mumu on 2019/3/27.
 */
@Data
public class ResponseLeaveWordsToUserGet {

    private List<Msgs> data;

    private int total;

    @Data
    public static class Msgs{
        //发消息者id
        private int userId;

        //发消息者昵称
        private String nickname;

        //回复对象id
        private int answerUserId;

        //回复对象名称
        private String answerUserNickname;

        //留言时间
        private String createTime;

        //留言
        private String msg;

        private String avatar;

        private int gender;

    }
}
