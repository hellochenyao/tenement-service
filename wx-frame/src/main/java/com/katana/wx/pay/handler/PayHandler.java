package com.katana.wx.pay.handler;

import com.katana.wx.common.utils.XStreamFactory;
import com.katana.wx.pay.request.UnifiedOrderRequest;
import com.katana.wx.pay.response.UnifiedOrderResponse;
import com.thoughtworks.xstream.XStream;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * 支付接口中的转换处理类
 *
 * @author katana
 */
public class PayHandler {

    /**
     * 微信统一支付处理接口
     *
     * @param request
     * @return
     */
    public static String unifiedorder(UnifiedOrderRequest request) {
        String result = "";
        if (request != null) {
            XStream xStreamForRequestPostData = XStreamFactory.initSplitLine();
            xStreamForRequestPostData.alias("xml", UnifiedOrderRequest.class);
            result = xStreamForRequestPostData.toXML(request);
        }
        return result;
    }

    /**
     * xml格式的字符串
     *
     * @param xmlResult
     * @return
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static UnifiedOrderResponse unifiedOrderResponse(
            String xmlResult) {
        /*XStream xstream = new XStream(new DomDriver("UTF-8",
                new XmlFriendlyNameCoder("-_", "_")));*/
        XStream xstream = XStreamFactory.init(true);
        xstream.alias("xml", UnifiedOrderResponse.class);
        UnifiedOrderResponse orderResult = null;
        try {
            orderResult = (UnifiedOrderResponse) xstream.fromXML(xmlResult);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return orderResult;
    }
}