package com.katana.tenement.service.manager;

import com.katana.tenement.domain.entity.AdminUserEntity;
import com.katana.tenement.service.manager.bo.AdminUserBo;

public interface AdminUserService {
    AdminUserEntity login(AdminUserBo adminUserBo);
}
