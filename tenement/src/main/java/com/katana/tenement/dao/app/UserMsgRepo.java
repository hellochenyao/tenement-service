package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserMsgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMsgRepo extends CrudRepository<UserMsgEntity, Integer>, JpaSpecificationExecutor<UserMsgEntity> {

    @Query("select t from UserMsgEntity t where t.invitationId = :id and t.pid = 0")
    Page<UserMsgEntity> findByInvitationId(@Param("id") int id, Pageable pageable);

    @Query("select count(t) from UserMsgEntity t where t.invitationId = :id and t.pid = 0")
    int findMsgCountByInvitationId(@Param("id") int id);

    @Query("select t from UserMsgEntity t where t.pid = :id")
    Page<UserMsgEntity> findByPid(@Param("id") int id, Pageable pageable);

    @Query("select count(t) from UserMsgEntity t where t.pid = :id")
    int findMsgCountByPid(@Param("id") int id);

    List<UserMsgEntity> findByInvitationId(int invitationId);
}
