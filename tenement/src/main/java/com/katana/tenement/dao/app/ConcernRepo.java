package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ConcernRepo extends JpaSpecificationExecutor<ConcernEntity> , CrudRepository<ConcernEntity,Integer> {
    ConcernEntity findByUseridAndToUserid(int userid,int toUserid);

    @Query("select count(t) from ConcernEntity t where t.userid = :userid")
    Integer findConcernNums(@Param("userid") int userid);

    @Query("select count(t) from  ConcernEntity t where t.toUserid = :toUserid")
    Integer findAdmiresNums(@Param("toUserid") int toUserid);

}
