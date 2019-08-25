package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserBrowsingRecordEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserBrowsingRecordRepo extends CrudRepository<UserBrowsingRecordEntity, Integer>, JpaSpecificationExecutor<UserBrowsingRecordEntity> {

}
