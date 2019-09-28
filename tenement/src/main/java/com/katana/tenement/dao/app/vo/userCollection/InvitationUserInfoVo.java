package com.katana.tenement.dao.app.vo.userCollection;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvitationUserInfoVo {
    private Integer id;

    private String title;

    private int userId;

    private String content;

    private String publisher;

    private Integer type;

    private String location;

    private String latitude;

    private Double rental;

    private String enterNums;

    private LocalDateTime desiredDate;

    private Integer acceptedMedium;

    private Integer roomRentType;

    private Integer allowCallMe;

    private String weChat;

    private String school;

    private String grade;

    private String remark;

    private String housingImgs;

    private String housingVideos;

    private Integer viewTimes;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private String city;

    private LocalDateTime lastLoginTime;

    private String avatar;

    private int gender;

    private Integer supportNums=0;

    private int leaveMsgNums;

    private Integer status;
}
