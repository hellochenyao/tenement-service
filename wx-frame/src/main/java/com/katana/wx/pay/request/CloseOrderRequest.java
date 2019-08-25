package com.katana.wx.pay.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 关闭订单
 *
 * @author katana
 */
@Data
public class CloseOrderRequest extends BasicPayParams implements Serializable {

    private static final long serialVersionUID = 1L;
    private String out_trade_no; // 商户订单号

}
