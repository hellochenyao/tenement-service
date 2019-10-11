package com.katana.tenement.dao.app.impl;

import com.katana.tenement.dao.app.UserLikeDao;
import com.katana.tenement.dao.app.vo.userLike.GiveUserLikeFilterVo;
import com.katana.tenement.dao.app.vo.userLike.GiveUserLikeVo;
import com.katana.tenement.framework.dto.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserLikeDaoImpl implements UserLikeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Page<GiveUserLikeVo> findGiveLikeUser(GiveUserLikeFilterVo filterVo) {
        StringBuilder sql = new StringBuilder();
        sql.append("select sql_calc_found_rows a.like_user_id as user_id,o.nickName,o.avatar,a.* from user_like a left join tenement_invitation_detail e " +
                "on a.like_invitation_id = e.id left join user_info o " +
                "on a.like_user_id = o.id where e.user_id = ? ");
        List<Object> params = new ArrayList<>();
        params.add(filterVo.getUserId());
        sql.append(" order by a.update_time desc limit ? , ? ");
        params.add((filterVo.getPageNo()-1)*filterVo.getPageSize());
        params.add(filterVo.getPageSize());
        RowMapper<GiveUserLikeVo> rowMapper = new BeanPropertyRowMapper<>(GiveUserLikeVo.class);
        List<GiveUserLikeVo> list = jdbcTemplate.query(sql.toString(),params.toArray(),rowMapper);
        int total = jdbcTemplate.queryForObject("SELECT FOUND_ROWS()",Integer.class);
        Page<GiveUserLikeVo> page = new Page<>();
        page.setData(list);
        page.setTotal(total);
        return page;
    }
}
