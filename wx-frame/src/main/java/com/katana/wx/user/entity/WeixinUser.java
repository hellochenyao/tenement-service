package com.katana.wx.user.entity;

import lombok.Data;

import java.util.Date;

/**
 * 微信用户
 *
 * @author katana
 */
@Data
public class WeixinUser {
    private int subscribe; // 是否关注
    private String openid; // openid
    private String nickname; // 用户昵称
    private int sex; // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String language; //
    private String city; // 普通用户个人资料填写的城市
    private String province; // 用户个人资料填写的省份
    private String country; // 国家，如中国为CN
    private String headimgurl; // 用户头像，
    private Date subscribe_time; // 关注时间
    private String unionid; // unionid
    private String remark; // 备注
    private String privilege; // 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
    private int groupid; // 分组id

}
