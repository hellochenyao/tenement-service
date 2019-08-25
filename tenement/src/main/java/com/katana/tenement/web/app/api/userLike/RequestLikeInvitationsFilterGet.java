package com.katana.tenement.web.app.api.userLike;

import com.katana.tenement.domain.emuns.UserLikeEnum;
import lombok.Data;

@Data
public class RequestLikeInvitationsFilterGet {

    private int likeUserId;

    private int status = UserLikeEnum.LIKE.getCode();

    private int pageNo = 1;

    private int pageSize = 10;
}
