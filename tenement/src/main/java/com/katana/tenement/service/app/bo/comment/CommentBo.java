package com.katana.tenement.service.app.bo.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentBo {

    private int id;

    //动态id
    private int dynamicId;

    private String content;

    //评论人的id,
    private int userId;

    //被评论的人的id
    private int commentedId;

    //评论的消息的id
    private int messagedId;

    private LocalDateTime createTime;
}
