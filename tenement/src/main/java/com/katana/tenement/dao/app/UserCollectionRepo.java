package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserCollectionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserCollectionRepo extends CrudRepository<UserCollectionEntity, Integer>, JpaSpecificationExecutor<UserCollectionEntity> {

}
