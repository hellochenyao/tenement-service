package com.katana.wx.auth.request;

import java.util.Map;

/**
 * 抽象请求
 *
 * @author katana
 */
public abstract class AbstractRequest {

    /**
     * 返回请求参数
     *
     * @return
     */
    public abstract Map<String, String> getParams() throws Exception;
}
