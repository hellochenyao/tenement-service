package com.katana.tenement.framework.util;

import java.util.UUID;

/**
 * uuid工具
 *
 * @author admin
 * @date 17/4/1
 */
public class UNIDGenerateUtils {

    /**
     * 获取32的UNID
     *
     * @return 32的UNID  String
     */
    public static String getUnid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
