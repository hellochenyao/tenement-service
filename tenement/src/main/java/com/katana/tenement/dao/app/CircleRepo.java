package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.CircleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CircleRepo extends CrudRepository<CircleEntity,Integer> , JpaSpecificationExecutor<CircleEntity> {
    @Query(value = "SELECT t.* from circle t where if(?1!='0',t.id = ?1,1=1)",
            countQuery = "SELECT count(*) from circle t where if(?1!='0',t.id = ?1,1=1)",nativeQuery = true
    )
    Page<CircleEntity> findCicle(int id, Pageable pageable);

    CircleEntity findByCircleName(String circleName);

}
