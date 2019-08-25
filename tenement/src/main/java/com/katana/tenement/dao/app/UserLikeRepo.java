package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

public interface UserLikeRepo extends CrudRepository<UserLikeEntity,Integer> , JpaSpecificationExecutor<UserLikeEntity> {
    @Query("select t from UserLikeEntity t where t.likeInvitationId = :invitationId and t.status = :status")
    Page<UserLikeEntity> findUserLikeEntitiesByLikeInvitationId(@Param("invitationId") int invitationId, @Param("status") int status ,Pageable pageable);

    @Query("select t from UserLikeEntity t where t.likeUserId = :userId and t.status = :status")
    Page<UserLikeEntity> findUserLikeEntitiesByLikeUserId(@Param("userId") int userId,@Param("status") int status,Pageable pageable);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    UserLikeEntity findByLikeInvitationIdAndLikeUserId(int likeInvitationId,int likeUserId);

}
