package com.katana.tenement.service.app.bo.UserLike;

import com.katana.tenement.domain.emuns.UserLikeEnum;
import lombok.Data;

@Data
public class UserLikeBo {

    private int id;

    private int likeUserId;

    private int likeInvitationId;

    private int status = UserLikeEnum.PLAIN.getCode();

}
