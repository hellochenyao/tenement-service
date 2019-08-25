package com.katana.wx.pay.request;

import lombok.Data;

/**
 * 微信发红包请求参数
 *
 * @author katana
 */
@Data
public class RedBackRequest implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String nonce_str; // 随机字符串
    private String sign; // 签名
    private String mch_billno; // 商户订单号
    private String mch_id; // 商户号
    private String sub_mch_id; // 子商户号(可选)
    private String wxappid; // 公众账号appid
    private String nick_name; // 提供方账号
    private String send_name; // 商户名称
    private String re_openid; // 用户openid
    private int total_amount; // 付款金额
    private int min_value; // 最小红包金额
    private int max_value; // 最大红包金额
    private int total_num; // 红包发送总人数
    private String wishing; // 红包祝福语
    private String client_ip; // Ip地址
    private String act_name; // 活动名称
    private String remark; // 备注
    private String logo_imgurl; // 商户logo的url(可选)
    private String share_content; // 分享方案(可选)
    private String share_url; // 分享链接 (可选)
    private String share_imgurl; // 分享的图片(可选)
}
