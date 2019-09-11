package com.katana.tenement.service.app;

import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.invitationBrowsing.*;
import com.katana.tenement.service.app.bo.tenementInvitation.InvitationUserInfoBo;

public interface InvitationBrowsingService {

    void viewDetail(InvitationBrowsingBo invitationBrowsingBo);

    void collectInvitation(UserCollectionBo userCollectionBo);

    void leaveWord(UserMsgBo userMsgBo);

    Page<UserMsgEntity> findLeaveWord(UserMsgFilterBo userMsgFilterBo);

    Page<UserMsgEntity> findResponseLeaveWords(UserResponseMsgFilterBo userResponseMsgFilterBo);

    Integer findLeaveMsgNumsByInvitationId(int id);

    InvitationUserInfoBo findByInvitation(int id);
}
