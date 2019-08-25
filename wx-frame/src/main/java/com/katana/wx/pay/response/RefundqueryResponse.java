package com.katana.wx.pay.response;

import com.katana.wx.pay.request.BasicPayParams;
import lombok.Data;

/**
 * 查询退款的返回结果集
 *
 * @author katana
 */
@Data
public class RefundqueryResponse extends BasicPayParams {
    private String result_code; // 业务结果
    private String err_code; // 错误码
    private String err_code_des;// 错误描述
    private String device_info; // 设备号
    private String transaction_id; // 微信订单号
    private String out_trade_no; // 商户订单号
    private int total_fee; // 订单总金额
    private String fee_type; // 订单金额货币种类
    private int cash_fee; // 现金支付金额
    private String cash_fee_type; // 货币种类
    private int refund_fee; // 退款金额
    private int coupon_refund_fee; // 代金券或立减优惠退款金额
    private int refund_count; // 退款笔数
    private String out_refund_no_$n;// 商户退款单号
    private String refund_id_$n; // 微信退款单号
    private String refund_channel_$n; // 退款渠道
    private int refund_fee_$n; // 退款金额
    private String fee_type_$n; // 货币种类
    private int coupon_refund_fee_$n; // 代金券或立减优惠退款金额
    private int coupon_refund_count_$n; // 代金券或立减优惠使用数量
    private String coupon_refund_batch_id_$n_$m; // 代金券或立减优惠批次ID
    private String coupon_refund_id_$n_$m; // 代金券或立减优惠ID
    private int coupon_refund_fee_$n_$m; // 单个代金券或立减优惠支付金额
    private int refund_status_$n; // 退款状态

    public RefundqueryResponse() {
        super();
    }

}
