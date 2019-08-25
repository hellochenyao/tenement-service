package com.katana.wx.weapp.user.utils;

import com.katana.wx.common.utils.Pkcs7Encoder;

import java.util.Base64;

/**
 * @author katana
 * @date 2018/1/19
 */
public class WechatBizDataCrypt {

    public static String decode(String sessionKey, String encryptedData, String iv) {
        byte[] sessionKeyBy = Base64.getDecoder().decode(sessionKey);
        byte[] encryptedDataBy = Base64.getDecoder().decode(encryptedData);
        byte[] ivBy = Base64.getDecoder().decode(iv);
        byte[] dec = Pkcs7Encoder.decryptOfDiyIV(encryptedDataBy, sessionKeyBy, ivBy);

        return new String(dec);
    }

}
