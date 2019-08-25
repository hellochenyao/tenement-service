package com.katana.wx.pay.response;

import com.katana.wx.pay.request.BasicPayParams;
import lombok.Data;

/**
 * 基础的return_code为SUCCESS 是返回的结果集
 *
 * @author katana
 */
@Data
public class BasicReturnSuccess extends BasicPayParams {
    private String result_code; // 业务结果码
    private String err_code; // 错误代码
    private String err_code_des; // 错误代码描述
}
