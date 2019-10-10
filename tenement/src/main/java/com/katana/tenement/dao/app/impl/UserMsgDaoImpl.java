package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserMsgDao;
import com.katana.tenement.dao.app.vo.userMsg.UserMsgFilterVo;
import com.katana.tenement.domain.entity.UserMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserMsgDaoImpl implements UserMsgDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<UserMsgEntity> findNewWord(UserMsgFilterVo filterVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select SQL_CALC_FOUND_ROWS a.* from user_msg a LEFT JOIN tenement_invitation_detail t on a.invitation_id = t.id" +
                " where t.user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(filterVo.getUserId());
        RowMapper<UserMsgEntity> rowMapper = new BeanPropertyRowMapper<>(UserMsgEntity.class);
        sql.append(" order by a.create_time desc limit ? , ? ");
        params.add((filterVo.getPageNo()-1)*filterVo.getPageSize());
        params.add(filterVo.getPageSize());
        List<UserMsgEntity> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()",Integer.class);
        Page<UserMsgEntity> page = new Page<>();
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
