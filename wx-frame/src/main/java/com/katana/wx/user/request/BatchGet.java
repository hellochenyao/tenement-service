package com.katana.wx.user.request;

import lombok.Data;

/**
 * 批量获取信息的请求
 *
 * @author katana
 */
@Data
public class BatchGet implements java.io.Serializable {
    private String openid; // openid
    private String lang; // 语言
}
