package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserInfoDao;
import com.katana.tenement.domain.entity.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserInfoEntity> findRecords() {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from user_info order by id desc");
        RowMapper<UserInfoEntity> rowMapper = new BeanPropertyRowMapper<>(UserInfoEntity.class);
        List<UserInfoEntity> userInfo = jdbcTemplate.query(sql.toString(),rowMapper);
        return userInfo;
    }
}
