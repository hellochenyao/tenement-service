package com.katana.wx.auth.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

/**
 * 获取用户信息请求
 *
 * @author katana
 */
@Data
public class AuthUserRequest extends AbstractRequest implements Serializable {
    /**
     * 调用凭证
     */
    private String accessToken;
    /**
     * 普通用户的标识，对当前开发者帐号唯一
     */
    private String openid;
    /**
     * 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
     */
    private String lang = "zh-CN";

    @Override
    public Map<String, String> getParams() throws Exception {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("access_token", this.accessToken);
        params.put("openid", this.openid);
        params.put("lang", this.lang);
        return params;
    }

}
