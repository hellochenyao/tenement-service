package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.userBrowsing.UserBrowsingFilterVo;
import com.katana.tenement.framework.dto.page.Page;

public interface UserBrowsingRecordDao {
    Page<InvitationUserInfoVo> findBrowsingRecords(UserBrowsingFilterVo filterVo);
}
