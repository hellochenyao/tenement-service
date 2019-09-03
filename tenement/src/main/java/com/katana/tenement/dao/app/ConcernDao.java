package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.concern.ConcernFilterVo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.framework.dto.page.Page;

public interface ConcernDao {
    Page<UserInfoEntity> findConcernUsers(ConcernFilterVo concernFilterVo);
}
