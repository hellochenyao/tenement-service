package com.katana.wx.pay.response;


import lombok.Data;

/**
 * js验证回调参数
 *
 * @author katana
 */
@Data
public class JSResponse {
    private String timestamp; // 时间戳
    private String nonceStr; // 随机字符串
    private String signature; // 签名
    private String appId; // 应用的appId
    private String ticket; // 换取的ticket
    private String url; // url 注意此处应该与公众号中设置的一致
}
