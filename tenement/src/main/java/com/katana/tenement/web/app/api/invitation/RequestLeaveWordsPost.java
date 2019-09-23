package com.katana.tenement.web.app.api.invitation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestLeaveWordsPost {

    @ApiModelProperty("内容")
    private String msg;

    @ApiModelProperty("回复的消息ID  默认0 帖子留言  其他：回复留言")
    private int  answerMsgId= 0;

    private int time=0;

    private int responseUserId;
}
