package com.katana.tenement.service.app.bo.UserLike;

import com.katana.tenement.domain.emuns.UserLikeEnum;
import lombok.Data;

@Data
public class UserLikeFilterBo {

    private int likeUserId;

    private int likeInvitationId;

    private int status;

    private int pageNo;

    private int pageSize;
}
