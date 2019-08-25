package com.katana.wx.weapp.user.conn;


import com.katana.wx.common.conn.Connection;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.utils.ConvertJsonUtils;
import com.katana.wx.weapp.user.response.SessionKeyResponse;

import java.util.TreeMap;

/**
 * @author katana
 * @date 2018/1/20
 */
public class WeappUserConn extends Connection {

    private static String jscode2sessionUrl = "https://api.weixin.qq.com/sns/jscode2session";

    public static WechatResult jscode2session(String appid, String secret, String jsCode) {

        TreeMap<String, String> params = new TreeMap<>();

        params.put("appid", appid);
        params.put("secret", secret);
        params.put("js_code", jsCode);
        params.put("grant_type", "authorization_code");
        String jsonResult = HttpsDefaultExecute("POST", jscode2sessionUrl, params, "");

        WechatResult wechatResult = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, SessionKeyResponse.class, "session_key");
        return wechatResult;
    }
}
