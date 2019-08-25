package com.katana.wx.pay.request;


import lombok.Data;

/**
 * 查询退款请求对象
 *
 * @author katana
 */
@Data
public class RefundqueryRequest extends BasicPayParams {

    private String device_info; // 设备号
    private String transaction_id; // 微信订单号
    private String out_trade_no; // 商户订单号
    private String out_refund_no; // 商户退款单号
    private String refund_id; // 微信退款单号

    public RefundqueryRequest() {
        super();
    }

    public RefundqueryRequest(String deviceInfo, String transactionId,
                              String outTradeNo, String outRefundNo, String refundId) {
        super();
        device_info = deviceInfo;
        transaction_id = transactionId;
        out_trade_no = outTradeNo;
        out_refund_no = outRefundNo;
        refund_id = refundId;
    }
}
