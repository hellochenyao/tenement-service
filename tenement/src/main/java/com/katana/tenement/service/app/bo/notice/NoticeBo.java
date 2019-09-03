package com.katana.tenement.service.app.bo.notice;

import lombok.Data;

@Data
public class NoticeBo {

    private int toUserid;//用户id发给默认 -1发给全部

    private String content;

    private String createTime;

    private String updateTime;
}
