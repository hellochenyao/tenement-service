package com.katana.tenement.web.app.api.userBrowsing;

import lombok.Data;

@Data
public class RequestUserBrowsingFilterGet {
    private int userId;

    private int invitationId;

    private int status;

    private int pageNo = 1;

    private int pageSize = 10 ;
}
