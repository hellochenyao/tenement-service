package com.katana.tenement.web.app.api.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseCommentGet {

    private List<Comment> comments;

    private int total;

    @Data
    public static class Comment{

        private int id;

        private String content;

        //评论人的id,
        private int userId;

        private String userName;

        private String avatar;

        //被评论的人的id
        private int commentedId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime createTime;

    }
}
