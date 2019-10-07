package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationLogFilterVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

public interface TenementInvitationDao {
    Page<TenementInvitationEntity> findInvitation(TenementInvitationFilterVo tenementInvitationFilterVo);
    InvitationUserInfoVo queryInvitationUserInfo(int id);
    Page<InvitationUserInfoVo> findPublishLog(InvitationLogFilterVo filterVo);
}
