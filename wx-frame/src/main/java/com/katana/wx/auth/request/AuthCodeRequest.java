package com.katana.wx.auth.request;

import lombok.Data;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

/**
 * 获取授权code请求参数
 *
 * @author katana
 */
@Data
public class AuthCodeRequest extends AbstractRequest implements java.io.Serializable {
    public static final String CHARSET = "utf-8";
    /**
     * snsapi_base(不需要弹出授权页面,只能获取openid)
     */
    public static final String SCOPE_SNSAPIBASE = "snsapi_base";
    /**
     * 弹出授权页面(获取用户基本信息)
     */
    public static final String SCOPE_SNSPAIUSERINFO = "snsapi_userinfo";

    private String appid;
    /**
     * 回调地址，请使用urlencode对链接进行处理
     */
    private String redirect_uri;
    private String response_type = "code";
    private String scope;
    private String state;

    public AuthCodeRequest(String appid, String redirect_uri, String response_type, String scope, String state) {
        super();
        this.appid = appid;
        this.redirect_uri = redirect_uri;
        this.response_type = response_type;
        this.scope = scope;
        this.state = state;
    }

    /**
     * 返回该类的请求参数
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Map<String, String> getParams() throws UnsupportedEncodingException {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("appid", this.appid);
        params.put("redirect_uri", URLEncoder.encode(this.redirect_uri, CHARSET));
        params.put("response_type", this.response_type);
        params.put("scope", this.scope);
        params.put("state", this.state);
        return params;
    }
}