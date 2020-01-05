package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.ConcernDao;
import com.katana.tenement.dao.app.vo.concern.ConcernFilterVo;
import com.katana.tenement.domain.entity.UserInfoEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConcernDaoImpl implements ConcernDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<UserInfoEntity> findConcernUsers(ConcernFilterVo concernFilterVo) {
        int userid = concernFilterVo.getUserid();
        int type = concernFilterVo.getType();
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("select SQL_CALC_FOUND_ROWS t.* from concern a inner join user_info t on ");
        if(type==0){
            sql.append("a.to_userid = t.id where a.userid = ? ");
            params.add(userid);
        }else if (type==1){
            sql.append("a.userid = t.id where a.to_userid = ? ");
            params.add("userid");
        }
        sql.append(" and a.concern_type = ? order by a.create_time desc");
        params.add(concernFilterVo.getConcernType().toString());
        sql.append(" limit ?,? ");
        params.add((concernFilterVo.getPageNo()-1)*concernFilterVo.getPageSize());
        params.add(concernFilterVo.getPageSize());
        RowMapper<UserInfoEntity> rowMapper = new BeanPropertyRowMapper<>(UserInfoEntity.class);
        List<UserInfoEntity> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        final String totalSql = "SELECT FOUND_ROWS();";
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);
        Page page = new Page();
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
