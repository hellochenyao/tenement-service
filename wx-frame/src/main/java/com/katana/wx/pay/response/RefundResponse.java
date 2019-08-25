package com.katana.wx.pay.response;

import com.katana.wx.pay.request.BasicPayParams;
import lombok.Data;

/**
 * 申请退款
 *
 * @author katana
 */
@Data
public class RefundResponse extends BasicPayParams {

    private String result_code; // 业务结果
    private String err_code; // 错误代码
    private String err_code_des; // 错误代码描述
    private String device_info; // 设备号
    private String transaction_id; // 微信订单号
    private String out_trade_no; // 商户订单号
    private String out_refund_no; // 微信退款单号
    private String refund_channel; // 退款渠道
    private int refund_fee; // 退款金额
    private int total_fee; // 订单总金额
    private String fee_type; // 订单金额货币种类
    private int cash_fee; // 现金支付金额
    private int cash_refund_fee; // 现金退款金额
    private int coupon_refund_fee; // 代金券或立减优惠退款金额
    private int coupon_refund_count;// 代金券或立减优惠使用数量
    private String coupon_refund_id;// 代金券或立减优惠ID

    public RefundResponse() {
        super();
    }

}
