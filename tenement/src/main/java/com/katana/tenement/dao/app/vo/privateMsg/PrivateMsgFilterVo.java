package com.katana.tenement.dao.app.vo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgFilterVo {

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private String creteTime;

    private int pageNo;

    private int pageSize;

}
