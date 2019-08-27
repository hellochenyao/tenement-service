package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.PrivateMsgDao;
import com.katana.tenement.dao.app.vo.privateMsg.PrivateMsgFilterVo;
import com.katana.tenement.domain.entity.PrivateMsgEntity;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrivateMsgDaoImpl implements PrivateMsgDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Page<PrivateMsgEntity> findUserPrivateMsg(PrivateMsgFilterVo privateFilter) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from private_msg t WHERE 1=1");
        List<Object> param = new ArrayList<>();
        if(privateFilter.getReceiveUserid()!=null&& !StringUtils.isEmpty(privateFilter.getReceiveUserid())){
            sql.append(" and t.receive_userid = ? ");
            param.add(privateFilter.getReceiveUserid());
        }
        if(privateFilter.getUserid()!=null&&!StringUtils.isEmpty(privateFilter.getUserid())){
            sql.append(" or t.userid = ? ");
            param.add(privateFilter.getUserid());
        }
        sql.append(" order by t.create_time desc");
        if(privateFilter.getPageNo()!=0 && privateFilter.getPageSize()!=0){
            sql.append(" limit ?,? ");
            param.add((privateFilter.getPageNo()-1)*privateFilter.getPageSize());
            param.add(privateFilter.getPageSize());
        }
        RowMapper<PrivateMsgEntity> rowMapper = new BeanPropertyRowMapper<>(PrivateMsgEntity.class);
        List<PrivateMsgEntity> list = jdbcTemplate.query(sql.toString(),param.toArray(),rowMapper);
        Page<PrivateMsgEntity> page = new Page();
        page.setData(list);
        page.setTotal(list.size());
        return page;
    }
}
