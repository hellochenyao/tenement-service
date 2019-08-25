package com.katana.tenement.dao.app;

import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgFilterVo;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PrivateMsgRepo extends CrudRepository<PrivateMsgEntity,Integer> , JpaSpecificationExecutor<PrivateMsgEntity> {
    @Query("select t from PrivateMsgEntity where userid = :userId and receiveUserid = :receiveUserId")
    Page<PrivateMsgEntity> findHistoryMsg(@Param("userId") int userId, @Param("receiveUserId") int receiveUserId, Pageable pageable);
}
