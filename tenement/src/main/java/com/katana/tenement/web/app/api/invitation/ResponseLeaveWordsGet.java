package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

import java.util.List;

@Data
public class ResponseLeaveWordsGet {



    private List<DetailWords> details;

    private Integer total;

    @Data
    public static class DetailWords{

        private Integer id;

        //发消息者id
        private int userId;

        //发消息者昵称
        private String nickname;

        //留言时间
        private String createTime;

        private String avatar;

        private int gender;

        private String lastLoginTime;

        //留言
        private String msg;

        //回复留言的条数
        private int ResTotal;

        private List<ResDetailWords> resDetail;

        //回复消息
        @Data
        public static class ResDetailWords{

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

            private String lastLoginTime;

        }
    }
}
