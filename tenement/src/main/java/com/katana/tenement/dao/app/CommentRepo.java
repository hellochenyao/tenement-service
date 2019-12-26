package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepo extends CrudRepository<CommentEntity,Integer> , JpaSpecificationExecutor<CommentEntity> {
    Page<CommentEntity> findByDynamicId(Integer dynamicId, Pageable pageable);
}
