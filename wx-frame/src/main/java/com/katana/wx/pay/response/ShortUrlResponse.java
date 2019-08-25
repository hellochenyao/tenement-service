package com.katana.wx.pay.response;

import com.katana.wx.pay.request.BasicPayParams;
import lombok.Data;

/**
 * 转换短链接  返回结果
 *
 * @author katana
 */
@Data
public class ShortUrlResponse extends BasicPayParams implements java.io.Serializable {
    private String result_code;
    private String err_code;
    private String short_url;

}
