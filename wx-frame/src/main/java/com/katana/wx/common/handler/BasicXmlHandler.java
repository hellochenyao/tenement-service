package com.katana.wx.common.handler;


import com.katana.wx.common.entity.resp.RespBasicMsg;

/**
 * xml 处理类
 *
 * @author katana
 */
public interface BasicXmlHandler {

    /**
     * 将java对象转换为xml
     *
     * @param <T>
     * @param t   待转换的对象
     * @return 已经转换好的xml格式字符
     */
    public String toMsgXml(RespBasicMsg msg);
}
