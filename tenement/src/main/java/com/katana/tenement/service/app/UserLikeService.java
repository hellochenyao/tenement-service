package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.UserLikeEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.UserLike.UserLikeBo;
import com.katana.tenement.service.app.bo.UserLike.UserLikeFilterBo;

import java.util.List;

public interface UserLikeService {

    void save(UserLikeBo userLikebo);

    /**
     * 查询该帖子的所有点赞用户
     */
    Page<UserLikeEntity> getUserLikeListByInvitationId(UserLikeFilterBo userLikeFilterBo);

    /**
     * 查询该用户的所有点赞帖子
     */
    Page<UserLikeEntity> getInvitationLikeListByUserId(UserLikeFilterBo userLikeFilterBo);

    UserLikeBo findUserLikeByInvitationIdAndUserId(int invitationId,int userId);

    void updateInvitationSupportNums(int invitationId,int userId,int likeStatus);

}
