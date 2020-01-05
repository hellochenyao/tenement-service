package com.katana.tenement.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 用户信息 实体
 * <p>
 * Created by mumu on 2019/3/11.
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfoEntity {

    //用户唯一id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //头像
    @Column
    private String avatar;

    //昵称
    @Column(name = "nick_name")
    private String nickName;

    //性别
    @Column
    private int gender;

    //微信账号
    @Column
    private String weChat;

    //毕业学校
    @Column
    private String school;

    //年级
    @Column
    private int grade;

    //职业
    @Column
    private String occupation;

    //学历
    @Column
    private String eduBack;

    //学生/工作
    @Column
    private int isWorker;

    //电话
    @Column
    private String phone;

    private String signature;

    //位置
    @Column
    private String location;

    //城市
    @Column
    private String city;

    //省份
    @Column
    private String province;

    //国家
    @Column
    private String country;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "union_id")
    private String unionId;

    //最后一次登陆时间
    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    //第一次登陆时间
    @Column(name = "create_time")
    private LocalDateTime createTime;

    //账号状态 0 正常  1 下线不可见
    @Column
    private int status;

}
