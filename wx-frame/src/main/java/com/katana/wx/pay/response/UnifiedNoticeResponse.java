package com.katana.wx.pay.response;

import lombok.Data;

/**
 * 微信回调信息
 *
 * @author katana
 */
@Data
public class UnifiedNoticeResponse implements java.io.Serializable {
    /**
     *
     */
    private String return_code; // 返回状态
    private String return_msg; // 返回信息 (可选)
    private String appid; // 公众账号ID
    private String mch_id; // 商户号
    private String device_info; // 设备号 (可选)
    private String nonce_str; // 随机字符串
    private String sign; // 签名()
    private String result_code; // 业务结果
    private String err_code; // 错误代码 (可选)
    private String err_code_des; // 错误代码描述(可选)
    private String openid; // 用户标识
    private String is_subscribe; // 是否关注公众账号
    private String trade_type; // 交易类型
    private String bank_type; // 付款银行
    private int total_fee; // 总金额
    private String fee_type; // 货币种类 (可选)
    private int cash_fee; // 支付现金
    private String cash_fee_type; // 现金支付货币类型 (可选)
    private int coupon_fee; // 代金券或立减优惠金额(可选)
    private int coupon_count; // 代金券或立减优惠使用数量 (可选)
    private String coupon_batch_id_$n; // 代金券或立减优惠批次ID (可选)
    private String coupon_id_$n; // 代金券或立减优惠ID(可选)
    private int coupon_fee_$n; // 单个代金券或立减优惠支付金额 (可选)
    private String transaction_id; // 微信支付单号
    private String out_trade_no; // 商户订单号
    private String attach; // 商家数据包 (可选)
    private String time_end; // 支付完成时间
    private String sub_mch_id; //

}
