package com.katana.wx.pay.response;

import lombok.Data;

/**
 * 发送红包时 return_code 和result_code都为SUCCESS的时候有返回的结果对象
 *
 * @author katana
 */
@Data
public class RedBackResultSuccess implements java.io.Serializable {
    private String mch_billno; // 商户单号
    private String mch_id; // 商户号
    private String wxappid; // 公众账号appid
    private String re_openid; // 用户openid
    private int total_amount; // 付款金额

    public RedBackResultSuccess(String mch_billno, String mch_id,
                                String wxappid, String re_openid, int total_amount) {
        super();
        this.mch_billno = mch_billno;
        this.mch_id = mch_id;
        this.wxappid = wxappid;
        this.re_openid = re_openid;
        this.total_amount = total_amount;
    }

}
