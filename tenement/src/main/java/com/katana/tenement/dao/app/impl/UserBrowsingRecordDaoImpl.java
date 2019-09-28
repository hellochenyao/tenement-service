package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserBrowsingRecordDao;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.userBrowsing.UserBrowsingFilterVo;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserBrowsingRecordDaoImpl implements UserBrowsingRecordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<InvitationUserInfoVo> findBrowsingRecords(UserBrowsingFilterVo filterVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select SQL_CALC_FOUND_ROWS a.* ,i.we_chat,i.school,i.grade,i.last_login_time,i.avatar,i.gender , d.status from " +
                "user_browsing_record t left join tenement_invitation_detail a on t.invitation_id = a.id left join " +
                "user_like d on t.invitation_id = d.like_invitation_id left join " +
                "user_info i on t.user_id = i.id " +
                " where 1 = 1 ");
        List<Object> params = new ArrayList<>();
        if(filterVo.getInvitationId()!=0){
            sql.append(" and t.invitation_id = ? ");
            params.add(filterVo.getInvitationId());
        }
        if(filterVo.getUserId()!=0){
            sql.append(" and t.user_id = ? ");
            params.add(filterVo.getUserId());
        }
        if(filterVo.getStatus()!=0){
            sql.append(" and a.status = ? ");
        }
        sql.append("order by t.browsing_time desc limit ? , ? ");
        params.add((filterVo.getPageNo()-1)*filterVo.getPageSize());
        params.add(filterVo.getPageSize());
        int total = jdbcTemplate.queryForObject("select FOUND_ROWS()",Integer.class);
        RowMapper<InvitationUserInfoVo> rowMapper = new BeanPropertyRowMapper<>(InvitationUserInfoVo.class);
        List<InvitationUserInfoVo> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        Page<InvitationUserInfoVo> page = new Page<>();
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
