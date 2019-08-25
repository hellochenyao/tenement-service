package com.katana.wx.pay.request;


import lombok.Data;

/**
 * 查询订单
 *
 * @author katana
 */
@Data
public class QueryOrderRequet extends BasicPayParams implements
        java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String transaction_id; // 微信订单号
    private String out_trade_no; // 商户订单号

}
