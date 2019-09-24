package com.katana.tenement.service.app.bo.userBrowsing;

import lombok.Data;

@Data
public class UserBrowsingFilterBo {

    private int userId;

    private int invitationId;

    private int status;

    private int pageNo;

    private int pageSize;

}
