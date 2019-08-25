package com.katana.tenement.dao.manager;

import com.katana.tenement.dao.manager.vo.AdminUserVo;
import com.katana.tenement.domain.entity.AdminUserEntity;

public interface AdminUserDao {
    AdminUserEntity findByUsernameAndStatue(AdminUserVo adminUserVo);
}
