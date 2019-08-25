package com.katana.wx.pay.request;

import com.katana.wx.pay.annotation.NotSign;
import lombok.Data;

/**
 * 请求参数的基类
 *
 * @author katana
 */
@Data
public class BasicPayParams {
    @NotSign
    public static final String DEVICEINFO = "WEB";                //设备号   PC网页或公众号内支付请传"WEB"
    @NotSign
    public static final String TIMEFORMAT = "yyyyMMddHHmmss";        //订单时间格式
    private String appid; // 公众号id
    private String mch_id; // 商户号
    private String nonce_str; // 随机字符串
    private String sign; // 随机字符串
}
