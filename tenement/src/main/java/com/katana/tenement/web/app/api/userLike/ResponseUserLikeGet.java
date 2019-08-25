package com.katana.tenement.web.app.api.userLike;

import com.katana.tenement.domain.emuns.UserLikeEnum;
import lombok.Data;

@Data
public class ResponseUserLikeGet {
    private int id;

    private int likeUserId;

    private int likeInvitationId;

    private int status;
}
