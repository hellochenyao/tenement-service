package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.TestDao;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import io.swagger.annotations.Scope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

@Repository
@Scope(name="prototype",description = "prototype")
public class TestDaoImpl implements TestDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public List<TenementInvitationEntity> test( Integer id ,String city) throws InterruptedException {
        RowMapper<TenementInvitationEntity> rowMapper = new BeanPropertyRowMapper<>(TenementInvitationEntity.class);
        List<Object> param = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select * from tenement_invitation_detail where 1 = 1 ");
        if(StringUtils.isNotEmpty(city)&&city!=null){
            sql.append(" and city = ? ");
            param.add(city);
        }
        if(id !=null){
            sql.append(" and accepted_medium = ? ");
            param.add(id);
        }
        sql.append("for update");
        List<TenementInvitationEntity> tenementInvitationEntity = jdbcTemplate.query(sql.toString(),param.toArray(), rowMapper);
        out.println(tenementInvitationEntity);
        Thread.sleep(5000);
        return tenementInvitationEntity;
    }
}
