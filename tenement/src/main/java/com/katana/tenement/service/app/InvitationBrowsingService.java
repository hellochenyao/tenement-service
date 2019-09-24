package com.katana.tenement.service.app;

import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserBrowsingRecordEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.invitationBrowsing.*;
import com.katana.tenement.service.app.bo.tenementInvitation.InvitationUserInfoBo;
import com.katana.tenement.service.app.bo.userBrowsing.UserBrowsingFilterBo;

import java.util.List;

public interface InvitationBrowsingService {

    void viewDetail(InvitationBrowsingBo invitationBrowsingBo);

    void collectInvitation(UserCollectionBo userCollectionBo);

    UserMsgEntity leaveWord(UserMsgBo userMsgBo);

    Page<UserMsgEntity> findLeaveWord(UserMsgFilterBo userMsgFilterBo);

    Page<UserMsgEntity> findResponseLeaveWords(UserResponseMsgFilterBo userResponseMsgFilterBo);

    Integer findLeaveMsgNumsByInvitationId(int id);

    InvitationUserInfoBo findByInvitation(int id);

    UserMsgEntity getResponseMsgContent(int id);

    List<UserMsgEntity> findAllUserMsgs(int invitationId);

    void addBrowsingRecord(int userId,int invitationId);

    Page<InvitationUserInfoVo> findBrowsingInvitation(UserBrowsingFilterBo filterBo);
}
