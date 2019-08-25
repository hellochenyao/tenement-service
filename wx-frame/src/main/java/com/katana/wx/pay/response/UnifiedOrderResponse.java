package com.katana.wx.pay.response;

import com.katana.wx.pay.request.BasicPayParams;
import lombok.Data;

import java.io.Serializable;

/**
 * 下单时 return_code为SUCCESS时返回的对象
 *
 * @author katana
 */
@Data
public class UnifiedOrderResponse extends BasicPayParams implements Serializable {

    private String device_info; // 设备号
    private String result_code; // 业务返回码
    private String err_code; // 错误代码
    private String err_code_des; // 错误代码描述
    private String return_code;
    private String trade_type;
    private String prepay_id;
    private String code_url;
    private String return_msg;
}
