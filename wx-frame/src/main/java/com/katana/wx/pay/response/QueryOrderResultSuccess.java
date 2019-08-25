package com.katana.wx.pay.response;

import lombok.Data;

/**
 * 查询订单返回的结果
 *
 * @author katana
 */
@Data
public class QueryOrderResultSuccess {
    private String device_info;        //设备号
    private String openid;            //用户标识
    private String is_subscribe;    //是否关注公众号
    private String trade_type;        //交易类型
    private String trade_state;        //交易状态
    private String bank_type;        //付款银行
    private int total_fee;            //总金额(单位为分)
    private String fee_type;        //货币种类
    private int cash_fee;            //现金支付金额
    private String cash_fee_type;    //现金支付类型
    private int coupon_fee;        //代金券或立减优惠金额;
    private int coupon_count;    //代金卷使用数量
    private String coupon_batch_id_$n;//代金券或立减优惠批次ID
    private String coupon_id_$n;      //代金券或立减优惠ID
    private int coupon_fee_$n;          //单个代金券或立减优惠支付金额
    private String transaction_id;      //微信支付订单号
    private String out_trade_no;      //商户订单号
    private String attach;              //商家数据包
    private String time_end;          //支付完成时间
    private String trade_state_desc;  //交易状态描述
}
