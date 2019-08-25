package com.katana.wx.weapp.user.response;

import lombok.Data;

/**
 * @author katana
 * @date 2018/1/19
 */
@Data
public class WxUserInfo {

    private String openId;
    private String nickName;
    private String gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private Watermark watermark;

    @Data
    public static class Watermark {
        private String appid;
        private String timestamp;
    }
}
