package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.domain.entity.UserRelationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRelationRepo extends CrudRepository<UserRelationEntity,Integer>, JpaSpecificationExecutor<UserRelationEntity> {
    @Query("select a from UserRelationEntity t inner join UserInfoEntity a on t.userid = a.id where t.userid = :userid and t.type = :type")
    Page<UserInfoEntity> findByUseridAndType(@Param("userid") int userid, @Param("type") int type, Pageable page);

    void deleteByUserid(int userid);

    UserRelationEntity findByUseridAndFriendId(int userid,int friendId);
}
