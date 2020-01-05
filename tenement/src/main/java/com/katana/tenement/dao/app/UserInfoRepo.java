package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by mumu on 2019/4/18.
 */
public interface UserInfoRepo extends CrudRepository<UserInfoEntity, Integer>, JpaSpecificationExecutor<UserInfoEntity> {

    Page<UserInfoEntity> findByIdLikeOrNickName(int id, String content, Pageable pageable);

    UserInfoEntity findById(int userId);

    UserInfoEntity findByOpenId(String openId);

}
