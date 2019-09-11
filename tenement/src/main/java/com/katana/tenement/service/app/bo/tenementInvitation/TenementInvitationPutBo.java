package com.katana.tenement.service.app.bo.tenementInvitation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TenementInvitationPutBo {

    private Integer id;

    private String title;

    private String content;

    private String location;

    private Double rental;

    private LocalDate desiredDate;

    private String houseLayOut;

    private String remark;

    private String housingImgs;

    private String housingVideos;

    private Integer type;

    private Integer roomRentType;
}
