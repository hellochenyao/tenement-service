package com.katana.tenement.service.app.bo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgBo {

    private String content;

    //发送方用户id
    private Integer userid;

    //接收方用户id
    private Integer receiveUserid;

    private String createTime;

    private String updateTime;

}
