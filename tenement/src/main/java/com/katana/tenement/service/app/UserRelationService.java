package com.katana.tenement.service.app;

import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.userRelation.UserRelationBo;
import com.katana.tenement.service.app.bo.userRelation.UserRelationFilterBo;
import com.katana.tenement.service.app.bo.userinfo.UserInfoBo;

import java.util.List;

public interface UserRelationService {

    void createFriend(UserRelationBo userRelationBo);

    Page<UserInfoBo> findFriend(UserRelationFilterBo userRelationFilterBo);

    void deleteFriend(int userid,int friendId);
}
