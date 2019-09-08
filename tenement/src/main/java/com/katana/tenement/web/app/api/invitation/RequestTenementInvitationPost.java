package com.katana.tenement.web.app.api.invitation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestTenementInvitationPost {

    @ApiModelProperty("帖子标题")
    private String title;

    @ApiModelProperty("帖子内容")
    private String content;

    @ApiModelProperty("期望地点 经纬度 经度，维度")
    private String location;

    @ApiModelProperty("预算")
    private Double rental;

    @ApiModelProperty("入住人数")
    private String enterNums;

    @ApiModelProperty("期望入住时间")
    private String desiredDate;

    @ApiModelProperty("户型")
    private String houseLayOut;

    @ApiModelProperty("中介免打扰 （0拒绝中介 1接收中介)")
    private Integer acceptedMedium;

    @ApiModelProperty("是否可以直接打电话给我 (0不可以 1可以)")
    private Integer allowCallMe;

    @ApiModelProperty("是否显示个人信息 （0显示 1不显示）")
    private int showPersonalInfo;

    @ApiModelProperty("房源图片")
    private String housingImgs;

    @ApiModelProperty("房源视频")
    private String housingVideos;

    @ApiModelProperty("帖子类型（0 求租 1房源 2转租）")
    private Integer type;

    @ApiModelProperty("出租类型（0 整租 1 短租 2合租）")
    private Integer roomRentType;


}
