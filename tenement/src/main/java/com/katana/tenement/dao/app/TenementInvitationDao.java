package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.framework.dto.page.Page;

public interface TenementInvitationDao {
    Page<TenementInvitationEntity> findInvitation(TenementInvitationFilterVo tenementInvitationFilterVo);
}
