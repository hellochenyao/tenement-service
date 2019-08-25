package com.katana.wx.jssdk.conn;

import com.katana.wx.common.conn.Connection;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.utils.ConvertJsonUtils;
import com.katana.wx.jssdk.request.JsapiTicketRequest;
import com.katana.wx.jssdk.response.JsapiTicketResponse;

import java.util.TreeMap;

/**
 * 公众号用于调用微信JS接口的临时票据
 *
 * @author katana
 * @date 2017/5/18
 */
public class JsapiConnection extends Connection {

    // 获取公众号用于调用微信JS接口的临时票据的路径
    private final static String jsapi_ticket_path = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public static WechatResult getJsapiTicket(JsapiTicketRequest request) {

        WechatResult result = new WechatResult();
        try {
            TreeMap<String, String> map = new TreeMap<String, String>();
            map.put("type", "jsapi");
            map.put("access_token", request.getAccess_token());
            String jsonResult = HttpDefaultExecute("GET", jsapi_ticket_path, map, "");
            result = ConvertJsonUtils.ConvertJavaObjectByKeyword(jsonResult, JsapiTicketResponse.class, "ticket");
        } catch (Exception e) {
            result.setObj(e.getMessage());
        }
        return result;
    }
}
