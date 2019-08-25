package com.katana.wx.weapp.user.response;

import lombok.Data;

/**
 * @author katana
 * @date 2018/1/19
 */
@Data
public class WxUserPhone {

    private String phoneNumber;//用户绑定的手机号（国外手机号会有区号）
    private String purePhoneNumber;//没有区号的手机号
    private String countryCode;//区号
    private Watermark watermark;

    @Data
    public static class Watermark {
        private String appid;
        private String timestamp;
    }

}
