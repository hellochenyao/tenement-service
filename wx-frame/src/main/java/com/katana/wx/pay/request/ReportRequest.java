package com.katana.wx.pay.request;

import lombok.Data;

/**
 * 测速上报接口
 *
 * @author katana
 */
@Data
public class ReportRequest extends BasicPayParams {
    private String device_info; // 设备号
    private String interface_url; // 接口url
    private int execute_time; // 接口耗时
    private String return_code; // 返回状态码
    private String return_msg; // 返回信息
    private String result_code; // 业务结果
    private String err_code; // 错误代码
    private String err_code_des; // 错误代码描述
    private String out_trade_no; // 商户订单号
    private String user_ip; // 访问接口IP
    private String time; // 商户上报时间
}
