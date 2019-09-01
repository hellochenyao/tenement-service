package com.katana.tenement.dao.app;

import com.katana.tenement.domain.entity.UserInfoEntity;

import java.util.List;

public interface UserInfoDao {
    List<UserInfoEntity> findRecords();
}
