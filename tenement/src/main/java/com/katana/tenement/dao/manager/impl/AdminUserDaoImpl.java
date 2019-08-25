package com.katana.tenement.dao.manager.impl;

import com.katana.tenement.dao.manager.AdminUserDao;
import com.katana.tenement.dao.manager.vo.AdminUserVo;
import com.katana.tenement.domain.entity.AdminUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AdminUserDaoImpl implements AdminUserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public AdminUserEntity findByUsernameAndStatue(AdminUserVo adminUserVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from admin_user where username = ? and password = ? and status = ?");
        List params = new ArrayList();
        params = Arrays.asList(adminUserVo.getUsername(), adminUserVo.getPassword(), 1);
        RowMapper<AdminUserEntity> rowMapper = new BeanPropertyRowMapper<>(AdminUserEntity.class);
        AdminUserEntity adminUser = jdbcTemplate.queryForObject(sql.toString(), params.toArray(), rowMapper);
        return adminUser;
    }
}
