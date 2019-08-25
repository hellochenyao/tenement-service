package com.katana.tenement.dao.app.vo.tenementInvitation;

import lombok.Data;

@Data
public class TenementInvitationFilterVo {

    private Integer ascending;

    private Integer type;

    private String title;

    //所在城市
    private String city;

    private Integer pageNo;

    private Integer pageSize;

}