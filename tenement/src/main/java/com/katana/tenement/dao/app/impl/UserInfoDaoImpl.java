package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserInfoDao;
import com.katana.tenement.domain.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserInfoEntity findRecords() {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from user_info order by id desc limit 1");
        UserInfoEntity userInfo = jdbcTemplate.queryForObject(sql.toString(),UserInfoEntity.class);
        return userInfo;
    }
}
