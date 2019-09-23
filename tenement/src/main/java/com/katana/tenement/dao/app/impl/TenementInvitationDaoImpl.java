package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.TenementInvitationDao;
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
        sql.append("select SQL_CALC_FOUND_ROWS * from tenement_invitation_detail where 1=1 ");
        if (tenementInvitationFilterVo.getTitle() != null && StringUtils.isNotEmpty(tenementInvitationFilterVo.getTitle())) {
            sql.append(" and title like ? ");
            param.add("%" + tenementInvitationFilterVo.getTitle() + "%");
        }
        if (tenementInvitationFilterVo.getCity() != null && StringUtils.isNotEmpty(tenementInvitationFilterVo.getCity())) {
            sql.append(" and city like ? ");
            param.add(tenementInvitationFilterVo.getCity() + "%");
        }
        if (tenementInvitationFilterVo.getType() != null) {
            sql.append(" and type = ? ");
            param.add(tenementInvitationFilterVo.getType());
        }

        if(tenementInvitationFilterVo.getPublisherId()!=0){
            sql.append(" and user_id = ? ");
            param.add(tenementInvitationFilterVo.getPublisherId());
        }

        if(tenementInvitationFilterVo.getLocation()!=null&&StringUtils.isNotEmpty(tenementInvitationFilterVo.getLocation())){
            sql.append(" and location like ? ");
            param.add("%"+tenementInvitationFilterVo.getLocation()+"%");
        }

        if (tenementInvitationFilterVo.getAscending() != null && tenementInvitationFilterVo.getAscending() == 0) {
            sql.append(" order by update_time asc ");
        }
        if (tenementInvitationFilterVo.getAscending() != null && tenementInvitationFilterVo.getAscending() == 1) {
            sql.append(" order by update_time desc ");
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
        sql.append("select  temp1.* , temp2.we_chat,temp2.school,temp2.grade,temp2.last_login_time,temp2.avatar,temp2.gender , temp3.status from tenement_invitation_detail temp1 left JOIN user_info\n" +
                " temp2 on temp1.user_id = temp2.id left JOIN user_like \n" +
                "temp3 on temp1.user_id = temp3.like_user_id and temp1.id=temp3.like_invitation_id \n" +
                "where temp1.id=?");
        params.add(id);
        RowMapper<InvitationUserInfoVo> rowMapper = new BeanPropertyRowMapper<>(InvitationUserInfoVo.class);

        return jdbcTemplate.queryForObject(sql.toString(),params.toArray(),rowMapper);
    }
}
