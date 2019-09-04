package com.katana.tenement.service.app.bo.tenementInvitation;

import lombok.Data;


@Data
public class TenementInvitationFilterBo {

    private Integer ascending;

    private Integer type;

    private String title;

    private int publisherId;

    //所在城市
    private String city;

    private Integer pageNo;

    private Integer pageSize;

}
