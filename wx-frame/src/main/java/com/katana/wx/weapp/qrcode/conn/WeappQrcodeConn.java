package com.katana.wx.weapp.qrcode.conn;

import com.alibaba.fastjson.JSONObject;
import com.katana.wx.common.conn.Connection;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author katana
 * @date 2018/1/20
 */
public class WeappQrcodeConn extends Connection {

    private static String getwxacode = "https://api.weixin.qq.com/wxa/getwxacode";
    private static String getwxacodeunlimit = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";
    private static String createwxaqrcode = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";

    public static BufferedImage getWxacode(String accessToken, String path, String width) {

        TreeMap<String, String> params = new TreeMap<>();
        params.put("access_token", accessToken);

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("path", path);
        dataMap.put("width", width);
        String data = JSONObject.toJSONString(dataMap);
        BufferedImage bufferedImage = HttpsExecuteImage("POST", getwxacode, params, data);
        return bufferedImage;
    }

    public static BufferedImage getWxacodeunlimit(String accessToken, String scene, String page, String width) {

        TreeMap<String, String> params = new TreeMap<>();
        params.put("access_token", accessToken);

        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("scene", scene);
        dataMap.put("page", page);
        dataMap.put("width", width);
        String data = JSONObject.toJSONString(dataMap);
        BufferedImage bufferedImage = HttpsExecuteImage("POST", getwxacodeunlimit, params, data);
        return bufferedImage;
    }

    public static BufferedImage createWxaqrcode(String accessToken, String path, String width) {

        TreeMap<String, String> params = new TreeMap<>();
        params.put("access_token", accessToken);
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("path", path);
        dataMap.put("width", width);
        String data = JSONObject.toJSONString(dataMap);
        BufferedImage bufferedImage = HttpsExecuteImage("POST", createwxaqrcode, params, data);
        return bufferedImage;
    }
}
