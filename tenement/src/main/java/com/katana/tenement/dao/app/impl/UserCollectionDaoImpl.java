package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserCollectionDao;
import com.katana.tenement.dao.app.vo.userCollection.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.userCollection.UserCollectionFilterVo;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserCollectionDaoImpl implements UserCollectionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<InvitationUserInfoVo> findCollections(UserCollectionFilterVo filterVo) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("select SQL_CALC_FOUND_ROWS a.* ,i.we_chat,i.school,i.grade,i.last_login_time,i.avatar,i.gender from " +
                " user_collection t left join tenement_invitation_detail a on t.invitation_id = a.id " +
                " left join user_info i on t.user_id = i.id " +
                " where 1 = 1 ");
        sql.append(" and t.user_id = ? ");
        params.add(filterVo.getUserId());
        sql.append(" order by t.collect_time desc limit ? , ? ");
        params.add((filterVo.getPageNo()-1)*filterVo.getPageSize());
        params.add(filterVo.getPageSize());
        RowMapper<InvitationUserInfoVo> rowMapper = new BeanPropertyRowMapper<>(InvitationUserInfoVo.class);
        List<InvitationUserInfoVo> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        int total = jdbcTemplate.queryForObject("select found_rows()",Integer.class);
        Page<InvitationUserInfoVo> page = new Page<>();
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
