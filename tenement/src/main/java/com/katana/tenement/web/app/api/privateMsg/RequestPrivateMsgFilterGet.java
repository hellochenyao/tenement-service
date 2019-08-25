package com.katana.tenement.web.app.api.privateMsg;

import lombok.Data;

@Data
public class RequestPrivateMsgFilterGet {

    //接收方用户id
    private Integer receiveUserid;

    private String creteTime;

    private int pageNo=1;

    private int pageSize=10;
}
