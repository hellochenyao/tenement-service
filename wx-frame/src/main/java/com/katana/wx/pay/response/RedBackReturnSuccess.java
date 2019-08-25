package com.katana.wx.pay.response;

import lombok.Data;

/**
 * 发送红包 return_code为succes时返回的操作
 *
 * @author katana
 */
@Data
public class RedBackReturnSuccess {
    private String sign; // 签名
    private String result_code; // 业务结果
    private String err_code; // 错误代码(可选)
    private String err_code_des; // 错误代码描述

    public RedBackReturnSuccess() {
        super();
    }

    public RedBackReturnSuccess(String sign, String result_code,
                                String err_code, String err_code_des) {
        super();
        this.sign = sign;
        this.result_code = result_code;
        this.err_code = err_code;
        this.err_code_des = err_code_des;
    }

}
