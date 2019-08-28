package com.katana.tenement.dao.app.vo.privateMsg;

import lombok.Data;

@Data
public class PrivateMsgUserReceiveFilterVo {

    private Integer userid;//接收的用户id

    private int pageNo;

    private int pageSize;
}
