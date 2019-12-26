package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.DynamicEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface DynamicRepo extends CrudRepository<DynamicEntity,Integer>, JpaSpecificationExecutor<DynamicEntity> {
    @Query("select t from DynamicEntity t left join ConcernEntity c on t.circleId=c.toUserid where c.userid = ?1")
    Page<DynamicEntity> findJoinConcern(Integer userId,Pageable pageable);
}
