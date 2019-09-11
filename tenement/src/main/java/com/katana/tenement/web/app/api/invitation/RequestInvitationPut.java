package com.katana.tenement.web.app.api.invitation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestInvitationPut {

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("帖子内容")
    private String content;

    @ApiModelProperty("期望地点")
    private String location;

    @ApiModelProperty("预算")
    private Double rental;

    @ApiModelProperty("期望入住时间")
    private String desiredDate;

    @ApiModelProperty("户型")
    private String houseLayOut;

    private String remark;

    @ApiModelProperty("房源图片")
    private String housingImgs;

    @ApiModelProperty("房源视频")
    private String housingVideos;

    @ApiModelProperty("帖子类型（0 求租 1房源 2转租）")
    private Integer type;

    @ApiModelProperty("出租类型（0 整租 1 短租 2合租）")
    private Integer roomRentType;

    @ApiModelProperty("所在城市")
    private String city;

}
