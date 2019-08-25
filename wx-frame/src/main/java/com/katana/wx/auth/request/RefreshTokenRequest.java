package com.katana.wx.auth.request;

import lombok.Data;

import java.util.Map;
import java.util.TreeMap;

/**
 * 刷新token请求
 *
 * @author katana
 */
@Data
public class RefreshTokenRequest implements java.io.Serializable {
    private String appid;
    private String grant_type = "refresh_token";
    private String refresh_token;

    public RefreshTokenRequest() {
    }

    /**
     * 获取该类的请求参数
     *
     * @return
     */
    public Map<String, String> getParams() {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("appid", this.appid);
        params.put("grant_type", this.grant_type);
        params.put("refresh_token", this.refresh_token);
        return params;
    }

}
