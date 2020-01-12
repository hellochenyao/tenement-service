package com.katana.tenement.service.app;

import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import com.katana.tenement.service.app.bo.tenementInvitation.InvitationPublishLogBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationFilterBo;
import com.katana.tenement.service.app.bo.tenementInvitation.TenementInvitationPutBo;

public interface TenementInvitationService {

    //发布帖子
    TenementInvitationEntity create(TenementInvitationBo tenementInvitationBo);

    //查找帖子
    Page<TenementInvitationEntity> findInvitations(TenementInvitationFilterBo tenementInvitationFilterBo);

    void updateInvitation(TenementInvitationPutBo tenementInvitationPutBo);

    void deleteInvitation(int id);

    Page<UserMsgEntity> findUserMsgs(int invitationId);

    Page<InvitationUserInfoVo> findPublishLog(InvitationPublishLogBo logBo);

    void refreshInvitation(int invitationId);

    void setInvitationStatus(int invitationId,int status);

    TenementInvitationEntity findInvitationById(int id);
}
