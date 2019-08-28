package com.katana.tenement.web.app.api.privateMsg;

import lombok.Data;

@Data
public class RequestPrivateMsgReceiveFilterGet {
    private int pageNo=1;

    private int pageSize=10;
}
