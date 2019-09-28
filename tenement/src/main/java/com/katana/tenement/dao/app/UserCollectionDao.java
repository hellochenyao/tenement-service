package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.userCollection.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.userCollection.UserCollectionFilterVo;
import com.katana.tenement.framework.dto.page.Page;

public interface UserCollectionDao {

    Page<InvitationUserInfoVo> findCollections(UserCollectionFilterVo filterVo);

}
