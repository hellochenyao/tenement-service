package com.katana.wx.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取属性文件
 *
 * @author katana
 */
public class PropertiesUtils {

    /**
     * 根据key值获取对应的value值
     *
     * @param key  key值
     * @param path 表示属性文件的路径
     * @return
     */
    public String getPropertiesValue(String key, String path) {
        String result = "";
        if (key != null && !"".equals(key)) {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream(path);
            Properties properties = new Properties();
            try {
                properties.load(input);
                result = properties.getProperty(key, "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
