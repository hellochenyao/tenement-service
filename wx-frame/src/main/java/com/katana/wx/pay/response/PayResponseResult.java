package com.katana.wx.pay.response;

import com.katana.wx.common.entity.results.BaseXmlObj;
import lombok.Data;

/**
 * 支付返回的结果
 *
 * @author katana
 */
@Data
public class PayResponseResult extends BaseXmlObj implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String return_code; // 返回状态码
    private String return_msg; // 返回信息
}
