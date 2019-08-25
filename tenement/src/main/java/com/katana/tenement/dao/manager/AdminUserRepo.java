package com.katana.tenement.dao.manager;

import com.katana.tenement.domain.entity.AdminUserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AdminUserRepo extends CrudRepository<AdminUserEntity, Integer>, JpaSpecificationExecutor<AdminUserEntity> {
    AdminUserEntity findByUsernameAndPasswordAndStatus(String username, String password, Integer status);
}
