package com.katana.wx.common.conn;

import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.request.AccessTokenRequest;
import com.katana.wx.common.response.AccessTokenResponse;
import com.katana.wx.common.utils.ConvertJsonUtils;

import java.util.TreeMap;

/**
 * 微信基础的接口
 *
 * @author katana
 * @date 2017/5/18
 */
public class BaseConnection extends Connection {

    // 获取access_token的路径
    private final static String token_path = "https://api.weixin.qq.com/cgi-bin/token";

    public static WechatResult getAccessToken(AccessTokenRequest request) {

        WechatResult result = new WechatResult();
        try {
            TreeMap<String, String> map = new TreeMap<String, String>();
            map.put("grant_type", "client_credential");
            map.put("appid", request.getKey());
            map.put("secret", request.getSecret());
            String jsonResult = HttpDefaultExecute("GET", token_path, map, "");
            result = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, AccessTokenResponse.class, "access_token");
        } catch (Exception e) {
            result.setObj(e.getMessage());
        }
        return result;
    }
}
