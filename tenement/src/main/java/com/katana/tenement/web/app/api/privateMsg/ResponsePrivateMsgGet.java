package com.katana.tenement.web.app.api.privateMsg;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePrivateMsgGet {

    private List<Message> messages;

    private int total;

    @Data
    public static class Message{
        private String content;

        private Integer userid;

        private String userNickName;

        private String userAvatar;

        private Integer receiveUserid;

        private String receiveUserAvatar;

        private String receiveUserNickName;

        private int isRead;//-1未读 0已读

        private String type;

        private String createTime;
    }
}
