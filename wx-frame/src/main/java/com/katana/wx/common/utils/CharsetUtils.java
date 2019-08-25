package com.katana.wx.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * 编码工具类
 * 主要应用场景是常用编码的互转
 *
 * @author katana
 */
public class CharsetUtils {

    public static final String UTF8 = "utf-8";            //utf-8的编码
    public static final String GBK = "gbk";                //gbk的编码
    public static final String GB2312 = "GB2312";

    /**
     * 将带转换的字符串转换为utf-8编码的字符串
     *
     * @param data
     * @return
     */
    public static String convertToUTF8(String data) {
        String result = "";
        try {
            result = new String(data.getBytes("iso8859-1"), "utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result = data;
        }
        return result;
    }
}
