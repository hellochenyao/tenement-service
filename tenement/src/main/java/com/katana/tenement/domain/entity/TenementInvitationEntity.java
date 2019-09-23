package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tenement_invitation_detail")
public class TenementInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //帖子标题
    @Column(name = "title")
    private String title;

    //帖子内容
    @Column(name = "content",columnDefinition="text")
    private String content;

    //发布人
    @Column(name = "publisher")
    private String publisher;

    //帖子类型（0 求租 1房源 2转租）
    @Column
    private Integer type;

    @Column
    private Integer userId;

    //------------------------无房帖子信息---------------------------
    //是否显示个人信息
    @Column
    private Integer showPersonalInfo;

    //中介免打扰 （0拒绝中介 1接收中介)
    @Column(name = "accepted_medium")
    private Integer acceptedMedium;

    //是否可以直接打电话给我 (0不可以 1可以)
    @Column(name = "allow_call_me")
    private Integer allowCallMe;

    //------------------------有房帖子信息---------------------------
    //户型
    @Column(name = "house_layout")
    private String houseLayOut;

    //出租类型（0 整租 1 主卧 2次卧 3床位）
    @Column(name = "room_rent_type")
    private Integer roomRentType;

    //------------------------帖子一些公共信息---------------------------
    private String remark;

    //房源图片
    @Column(name = "housing_imgs")
    private String housingImgs;

    //房源视频
    @Column(name = "housing_videos")
    private String housingVideos;

    //房源位置 或 期望入住地点
    @Column(name = "location")
    private String location;

    //房源具体位置(经纬度）
    private String latitude;

    //租金
    @Column(name = "rental")
    private double rental;

    private String enterNums;

    //期望入住时间 日期
    @Column(name = "desired_date")
    private LocalDate desiredDate;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    //浏览次数
    @Column(name = "view_times")
    private Integer viewTimes=0;

    //状态
    @Column
    private Integer status;

    //顶的数量
    @Column(name="support_nums")
    private Integer supportNums=0;

    //所在城市
    private String city;
}
