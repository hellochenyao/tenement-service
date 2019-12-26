package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.TenementInvitationDao;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationLogFilterVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.InvitationUserInfoVo;
import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TenementInvitationDaoImpl implements TenementInvitationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<TenementInvitationEntity> findInvitation(TenementInvitationFilterVo tenementInvitationFilterVo) {
        StringBuilder sql = new StringBuilder();
        List param = new ArrayList<>();
        sql.append("select SQL_CALC_FOUND_ROWS t.* from tenement_invitation_detail t where 1=1 ");
        if (tenementInvitationFilterVo.getTitle() != null && StringUtils.isNotEmpty(tenementInvitationFilterVo.getTitle())) {
            sql.append(" and t.title like ? ");
            param.add("%" + tenementInvitationFilterVo.getTitle() + "%");
        }
        if (tenementInvitationFilterVo.getCity() != null && StringUtils.isNotEmpty(tenementInvitationFilterVo.getCity())) {
            sql.append(" and t.city like ? ");
            param.add(tenementInvitationFilterVo.getCity() + "%");
        }
        if (tenementInvitationFilterVo.getType() != null) {
            if(tenementInvitationFilterVo.getType()==0){
                sql.append(" and t.type = 0 ");
            }else{
                sql.append(" and t.type = 1 or t.type = 2 ");
            }
        }

        if(tenementInvitationFilterVo.getPublisherId()!=0){
            sql.append(" and t.user_id = ? ");
            param.add(tenementInvitationFilterVo.getPublisherId());
        }

        if(tenementInvitationFilterVo.getLocation()!=null&&StringUtils.isNotEmpty(tenementInvitationFilterVo.getLocation())){
            sql.append(" and t.location like ? ");
            param.add("%"+tenementInvitationFilterVo.getLocation()+"%");
        }
        sql.append(" and t.`status` = 1 ");
        if (tenementInvitationFilterVo.getAscending() != null && tenementInvitationFilterVo.getAscending() == 0) {
            sql.append(" order by t.update_time asc ");
        }
        if (tenementInvitationFilterVo.getAscending() != null && tenementInvitationFilterVo.getAscending() == 1) {
            sql.append(" order by t.update_time desc ");
        }
        if (tenementInvitationFilterVo.getPageNo() != 0 && tenementInvitationFilterVo.getPageSize() != 0) {
            sql.append(" limit ?,?");
            param.add((tenementInvitationFilterVo.getPageNo() - 1) * tenementInvitationFilterVo.getPageSize());
            param.add(tenementInvitationFilterVo.getPageSize());
        }
        final String totalSql = "SELECT FOUND_ROWS();";
        RowMapper<TenementInvitationEntity> rowMapper = new BeanPropertyRowMapper<>(TenementInvitationEntity.class);
        List<TenementInvitationEntity> listData = jdbcTemplate.query(sql.toString(), param.toArray(), rowMapper);
        Page<TenementInvitationEntity> page = new Page<>();
        Integer total = jdbcTemplate.queryForObject(totalSql, Integer.class);
        page.setData(listData);
        page.setTotal(total);
        return page;
    }

    @Override
    public InvitationUserInfoVo queryInvitationUserInfo(int id) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("select  temp1.* , temp2.we_chat,temp2.school,temp2.grade,temp2.last_login_time,temp2.avatar,temp2.gender , temp1.status , temp3.status as like_status from tenement_invitation_detail temp1 left JOIN user_info\n" +
                " temp2 on temp1.user_id = temp2.id left JOIN user_like \n" +
                "temp3 on temp1.user_id = temp3.like_user_id and temp1.id=temp3.like_invitation_id \n" +
                "where temp1.id=?");
        params.add(id);
        RowMapper<InvitationUserInfoVo> rowMapper = new BeanPropertyRowMapper<>(InvitationUserInfoVo.class);

        return jdbcTemplate.queryForObject(sql.toString(),params.toArray(),rowMapper);
    }

    @Override
    public Page<InvitationUserInfoVo> findPublishLog(InvitationLogFilterVo filterVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select SQL_CALC_FOUND_ROWS  temp1.* , temp2.we_chat,temp2.school,temp2.grade,temp2.last_login_time,temp2.avatar,temp2.gender , temp1.status from tenement_invitation_detail temp1 left JOIN user_info\n" +
                " temp2 on temp1.user_id = temp2.id left JOIN user_like \n" +
                "temp3 on temp1.user_id = temp3.like_user_id and temp1.id=temp3.like_invitation_id \n" +
                "where temp1.user_id=? ");
        List<Object> params = new ArrayList<>();
        params.add(filterVo.getUserId());
        sql.append("limit ?,?");
        params.add((filterVo.getPageNo()-1)*filterVo.getPageSize());
        params.add(filterVo.getPageSize());
        RowMapper<InvitationUserInfoVo> rowMapper = new BeanPropertyRowMapper<>(InvitationUserInfoVo.class);
        List<InvitationUserInfoVo> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        Page<InvitationUserInfoVo> page = new Page<>();
        int total = jdbcTemplate.queryForObject("select FOUND_ROWS()",Integer.class);
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
