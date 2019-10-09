package com.katana.tenement.service.app;

import com.katana.tenement.dao.app.vo.userCollection.InvitationUserInfoVo;
import com.katana.tenement.domain.entity.UserCollectionEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionBo;
import com.katana.tenement.service.app.bo.userCollection.UserCollectionFilterBo;

public interface UserCollectionService {

    Boolean saveUserCollection(UserCollectionBo userCollectionBo);

    UserCollectionEntity queryCollectStatus(int userId,int invitationId);

    Page<InvitationUserInfoVo> queryAllCollectionInvitations(UserCollectionFilterBo filterBo);
}
