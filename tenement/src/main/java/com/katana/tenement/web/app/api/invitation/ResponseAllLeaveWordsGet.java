package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

import java.util.List;

@Data
public class ResponseAllLeaveWordsGet {

    private List<Msg> data;

    @Data
    public static class Msg{

        //发消息者id
        private int userId;

        private int time;

        //发消息者昵称
        private String nickname;

        //留言
        private String msg;

    }
}
