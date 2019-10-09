package com.katana.tenement.web.app.api.invitation;

import lombok.Data;

@Data
public class RequestUserMsgInfoGet {
    private int userId;

    private int pageNo=1;

    private int pageSize=10;
}
