package com.katana.wx.auth.conn;

import com.katana.wx.auth.request.AbstractRequest;
import com.katana.wx.auth.request.AuthTokenRequest;
import com.katana.wx.auth.request.AuthUserRequest;
import com.katana.wx.auth.request.RefreshTokenRequest;
import com.katana.wx.auth.response.AuthTokenResponse;
import com.katana.wx.auth.response.AuthUserInfo;
import com.katana.wx.common.conn.Connection;
import com.katana.wx.common.entity.results.ResultState;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.utils.ConvertJsonUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * 授权工具类
 *
 * @author katana
 */
public class AuthUtils extends Connection {
    /**
     * 授权链接
     */
    private static final String AUTHORIZE_PATH = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * 获取token的链接
     */
    private static final String ACCESS_TOKEN_PATH = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 刷新token
     */
    private static final String REFRESH_TOKEN_PATH = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 获取授权用户信息
     */
    private static final String USERINFO_PATH = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 判断用户accessToken是否有效
     */
    private static final String AUTH_PATH = "https://api.weixin.qq.com/sns/auth";

    private static AuthUtils self = null;

    public static AuthUtils me() {
        if (self == null) {
            self = new AuthUtils();
        }
        return self;
    }

    /**
     * 获取授权请求path
     *
     * @return
     * @throws Exception
     */
    public String getRequestPath(AbstractRequest request) throws Exception {
        Map<String, String> params = request.getParams();
        String path = setParmas(params, AUTHORIZE_PATH, "") + "#wechat_redirect";
        return path;
    }


    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openid      用户的唯一标识
     * @return { "errcode":0,"errmsg":"ok"}表示成功     { "errcode":40003,"errmsg":"invalid openid"}失败
     */
    public ResultState authToken(String accessToken, String openid) {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openid);
        String jsonResult = HttpDefaultExecute("GET", AUTH_PATH, params, "");
        ResultState state = ConvertJsonUtils.jsonToJavaObject(jsonResult, ResultState.class);
        return state;
    }

    /**
     * 通过code获取accessToken
     *
     * @param request AuthTokenRequest 必须给其赋值
     * @return result.success==true 返回的是 AuthTokenRequest 否则返回错误提示信息
     */
    public WechatResult getAccessTokenByCode(AuthTokenRequest request) {
        WechatResult result = new WechatResult();
        try {
            String jsonResult = HttpDefaultExecute("GET", ACCESS_TOKEN_PATH, request.getParams(), "");
            result = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, AuthTokenResponse.class, "access_token");
        } catch (Exception e) {
            result.setObj(e.getMessage());
        }
        return result;
    }

    /**
     * 刷新token
     *
     * @param request
     * @return result.success==true 返回的是 AuthTokenRequest 否则返回错误提示信息
     */
    public WechatResult getRefreshToken(RefreshTokenRequest request) {
        WechatResult result = new WechatResult();
        try {
            String jsonResult = HttpDefaultExecute("GET", REFRESH_TOKEN_PATH, request.getParams(), "");
            result = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, AuthTokenResponse.class, "access_token");
        } catch (Exception e) {
            result.setObj(e.getMessage());
        }
        return result;
    }

    /**
     * 获取网页授权页面请求
     *
     * @param request 请求参数对象
     * @return result.success==true 表示返回 AuthUserInfo  否则返回对应错误提示信息
     */
    public WechatResult getAuthUserInfo(AuthUserRequest request) {
        WechatResult result = new WechatResult();
        try {
            String jsonResult = HttpDefaultExecute("GET", USERINFO_PATH, request.getParams(), "");
            result = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, AuthUserInfo.class, "openid");
        } catch (Exception e) {
            result.setObj(e.getMessage());
        }
        return result;
    }
}
