package com.katana.wx.pay.request;

import lombok.Data;

/**
 * 转换短链接
 *
 * @author katana
 */
@Data
public class ShortUrlRequest extends BasicPayParams {
    private String long_url;  //url链接

}
