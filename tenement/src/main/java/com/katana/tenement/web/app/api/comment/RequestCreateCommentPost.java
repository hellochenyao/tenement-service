package com.katana.tenement.web.app.api.comment;

import lombok.Data;

@Data
public class RequestCreateCommentPost {

    //动态id
    private int dynamicId;

    private String content;

    //评论的消息的id
    private int messagedId=0;
}
