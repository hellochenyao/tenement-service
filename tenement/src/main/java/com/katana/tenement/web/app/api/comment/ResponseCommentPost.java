package com.katana.tenement.web.app.api.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseCommentPost {
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
