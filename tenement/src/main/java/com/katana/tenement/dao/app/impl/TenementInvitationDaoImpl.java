package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.TenementInvitationDao;
import com.katana.tenement.dao.app.vo.tenementInvitation.TenementInvitationFilterVo;
import com.katana.tenement.domain.entity.TenementInvitationEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

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
        sql.append("select * from tenement_invitation_detail where 1=1 ");
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
        RowMapper<TenementInvitationEntity> rowMapper = new BeanPropertyRowMapper<>(TenementInvitationEntity.class);
        List<TenementInvitationEntity> listData = jdbcTemplate.query(sql.toString(), param.toArray(), rowMapper);
        Page<TenementInvitationEntity> page = new Page<>();
        page.setData(listData);
        page.setTotal(listData.size());
        return page;
    }
}
