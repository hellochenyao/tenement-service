package com.katana.tenement.service.app.bo.invitationBrowsing;

import lombok.Data;

@Data
public class UserResponseMsgFilterBo {

    private int pid;

    private int invitationId;

    private int pageNo;

    private int pageSize;
}
