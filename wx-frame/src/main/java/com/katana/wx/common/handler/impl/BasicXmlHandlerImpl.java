package com.katana.wx.common.handler.impl;

import com.katana.wx.common.entity.resp.RespBasicMsg;
import com.katana.wx.common.handler.BasicXmlHandler;
import com.katana.wx.common.utils.XStreamFactory;
import com.thoughtworks.xstream.XStream;

/**
 * 处理类
 *
 * @author katana
 */
public class BasicXmlHandlerImpl implements BasicXmlHandler {

    /**
     *
     */
    public String toMsgXml(RespBasicMsg msg) {
        String result = "";
        if (msg != null) {
            XStream xs = XStreamFactory.init(true);
            xs.alias("xml", msg.getClass());
            result = xs.toXML(msg);
        }
        return result;
    }
}
