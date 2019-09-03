package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface NoticeRepo extends CrudRepository<NoticeEntity,Integer> , JpaSpecificationExecutor<NoticeEntity> {

}
