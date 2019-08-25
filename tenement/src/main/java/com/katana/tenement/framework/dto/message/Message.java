package com.katana.tenement.framework.dto.message;

import lombok.Data;

@Data
public class Message {

    private int userid;

    private String avatar;

    private String nickName;

    private String content;

    private String createTime;

}
