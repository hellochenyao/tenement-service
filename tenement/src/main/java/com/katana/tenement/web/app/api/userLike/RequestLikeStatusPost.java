package com.katana.tenement.web.app.api.userLike;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestLikeStatusPost {

    @ApiModelProperty("用户")
    private int likeUserId;

    @ApiModelProperty("帖子")
    private int likeInvitationId;

    @ApiModelProperty("0顶 1踩 -1正常")
    private int status;
}
