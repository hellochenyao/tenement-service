package com.katana.wx.pay.utils;

import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.common.utils.ConvertXMLUtils;
import com.katana.wx.common.utils.Signature;
import com.katana.wx.pay.conn.PayConnection;
import com.katana.wx.pay.request.UnifiedOrderRequest;
import com.katana.wx.pay.response.JSResponse;
import com.katana.wx.pay.response.PayResponseResult;
import com.katana.wx.pay.response.UnifiedNoticeResponse;
import com.katana.wx.pay.response.UnifiedOrderResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * 支付工具类
 *
 * @author katana
 */
public class PayUtils {

    /**
     * 初始化微信支付
     * (应用场景:在使用微信支付中,校验初始化话方法)
     *
     * @param ticketToken
     * @param url         需要校验的url
     * @param appid       应用id
     * @return JSResponse 对象
     */
    public static JSResponse initJSResponse(String ticketToken, String url, String appid) {
        Map<String, Object> params = new TreeMap<String, Object>();
        String noceStr = RandomStringGenerator.getRandomStringByLength(10);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        if (ticketToken != null && !"".equals(ticketToken)) {
            params.put("noncestr", noceStr);
            params.put("jsapi_ticket", ticketToken);
            params.put("timestamp", timestamp);
            params.put("url", url);
        }
        String sign = Signature.getSHA1Sign(params);
        JSResponse response = new JSResponse();
        response.setAppId(appid);
        response.setNonceStr(noceStr);
        response.setSignature(sign);
        response.setTicket(ticketToken);
        response.setTimestamp(timestamp);
        response.setUrl(url);
        return response;
    }

    /**
     * 微信支付回调函数
     *
     * @param packageId 统一支付产生的支付标识
     * @param appid     公众号 appid
     * @param apikey    公众号支付apiKey
     * @return
     */
    public static Map<String, Object> PayCallBack(String packageId, String appid, String apikey) {
        //package
        StringBuilder builder = new StringBuilder("prepay_id=");
        builder.append(packageId);
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("appId", appid);
        params.put("timeStamp", System.currentTimeMillis() / 1000);
        params.put("nonceStr", RandomStringGenerator.getRandomStringByLength(10));
        params.put("package", builder.toString());
        params.put("signType", "MD5");
        String sign = Signature.getSign(params, apikey);
        params.put("paySign", sign);
        return params;
    }

    /**
     * js 微信支付验证方法		(其中包括  PayCallBack 方法)
     *
     * @param request 统一下单请求
     * @param appid   公众号应用appid
     * @param apiKey  注意该为微信支付 apikey, 而不是公众号key
     * @return 如果成功则返回 WechatResult Map<String,String>对象,其中对象为
     */
    public static WechatResult PaySignRequest(UnifiedOrderRequest request, String appid, String apiKey) {
        WechatResult wechatResult = new WechatResult();            //返回对象
        String sign = Signature.getSignForObject(request, apiKey);        //将参数签名
        request.setSign(sign);
        WechatResult result = PayConnection.payOrder(request);
        UnifiedOrderResponse response = (UnifiedOrderResponse) result.getObj();
        if (response.getReturn_code().equalsIgnoreCase("success")) {        //判断是否支付成功
            String msg = result.getMsg();
            try {
                if (Signature.checkIsSignValidFromResponseString(msg, apiKey)) {
                    String packageId = response.getPrepay_id();
                    Map<String, Object> params = PayCallBack(packageId, appid, apiKey);
                    wechatResult.setSuccess(true);
                    wechatResult.setObj(params);
                    wechatResult.setMsg(msg);
                } else {
                    //校验失败
                    wechatResult.setMsg("validate signature fail!");
                }
            } catch (Exception e) {
                wechatResult.setObj(e.getMessage());
                wechatResult.setMsg("validate signature exception!");
            }
        } else {
            wechatResult.setMsg(result.getMsg());
        }
        return wechatResult;
    }

    /**
     * 微信支付通知方法
     * (应用场景: 在微信支付成功后的通知信息,)
     * input	输入流
     * charset  字符编码
     *
     * @return WechatResult success==true 时 返回 result.obj 返回 UnifiedNoticeResponse 其中result.msg 返回输入流的字符串  否则为错误错误信息
     */
    public static WechatResult wechatPayNotice(String data) {
        WechatResult result = new WechatResult();
        System.out.println("PayUtils wechatPayNotice  data------>" + data);
        UnifiedNoticeResponse response = new UnifiedNoticeResponse();
        result = ConvertXMLUtils.xmlStrToJavaObject(data, response);
        if (result.isSuccess()) {
            response = (UnifiedNoticeResponse) result.getObj();
            result.setSuccess(true);
            result.setObj(response);
            System.out.println("PayUtils success----->" + data);
        }
        return result;
    }

    /**
     * 支付成功后的支付通知数据信息
     *
     * @param resposne
     * @return
     */
    public static String payNoticData(PayResponseResult resposne) {
        String result = "";
        result = ConvertXMLUtils.toXmlData(resposne);
        return result;
    }
}
