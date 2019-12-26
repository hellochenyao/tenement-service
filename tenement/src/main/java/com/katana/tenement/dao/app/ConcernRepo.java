package com.katana.tenement.dao.app;

import com.katana.tenement.domain.emuns.ConcernType;
import com.katana.tenement.domain.entity.ConcernEntity;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcernRepo extends JpaSpecificationExecutor<ConcernEntity> , CrudRepository<ConcernEntity,Integer> {
    ConcernEntity findByUseridAndToUseridAndConcernType(int userid, int toUserid, ConcernType concernType);

    @Query("select count(t) from ConcernEntity t where t.userid = :userid and t.concernType = :concernType")
    Integer findConcernNums(@Param("userid") int userid,@Param("concernType") ConcernType concernType);

    @Query("select count(t) from  ConcernEntity t where t.toUserid = :toUserid and t.concernType = :concernType")
    Integer findAdmiresNums(@Param("toUserid") int toUserid,@Param("concernType") ConcernType concernType);

    List<ConcernEntity> findByToUseridAndConcernType(int toUserid,ConcernType concernType);
}
