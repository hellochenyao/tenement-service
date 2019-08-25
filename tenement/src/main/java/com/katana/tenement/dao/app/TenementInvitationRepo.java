package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.TenementInvitationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TenementInvitationRepo extends CrudRepository<TenementInvitationEntity, Integer>, JpaSpecificationExecutor<TenementInvitationEntity> {

//    @Query("SELECT t FROM TenementDetailInvitationEntity t WHERE t.type = :type ")
//    Page<TenementInvitationEntity> findByType(@Param("type") int type);
}
