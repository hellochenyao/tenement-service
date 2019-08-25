package com.katana.tenement.framework.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * emoji表情处理
 *
 * @author grw
 * @date 2018/12/20
 */
public class EmojiUtils {

    /**
     * emoji表情编码
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return str;
        }
    }

    /**
     * emoji表情解码
     *
     * @param str
     * @return
     */
    public static String decode(String str) {
        try {
            return new String(Base64.getDecoder().decode(str));
        } catch (Exception e) {
            return str;
        }
    }
}
