package com.katana.wx.pay.request;


import lombok.Data;

/**
 * 申请退款 请求参数
 *
 * @author katana
 */
@Data
public class RefundRequest extends BasicPayParams {

    private String device_info; // 设备号
    private String transaction_id; // 微信订单号
    private String out_trade_no; // 商户订单号
    private String out_refund_no; // 商户退款单号
    private int total_fee; // 总金额
    private int refund_fee; // 退款金额
    private String refund_fee_type; // 货币种类
    private String op_user_id; // 操作员

    public RefundRequest() {
        super();
    }

    public RefundRequest(String deviceInfo, String transactionId,
                         String outTradeNo, String outRefundNo, int totalFee, int refundFee,
                         String refundFeeType, String opUserId) {
        super();
        device_info = deviceInfo;
        transaction_id = transactionId;
        out_trade_no = outTradeNo;
        out_refund_no = outRefundNo;
        total_fee = totalFee;
        refund_fee = refundFee;
        refund_fee_type = refundFeeType;
        op_user_id = opUserId;
    }

}
