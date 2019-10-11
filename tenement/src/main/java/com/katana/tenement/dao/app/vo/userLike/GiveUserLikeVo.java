package com.katana.tenement.dao.app.vo.userLike;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiveUserLikeVo {

    private int userId;

    private String nickName;

    private String avatar;

    private String status;

    private LocalDateTime updateTime;

}
