package com.katana.wx.common.utils;

import com.katana.wx.common.entity.resp.RespBasicMsg;
import com.katana.wx.common.entity.results.BaseXmlObj;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.pay.response.UnifiedNoticeResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 转换工具类
 *
 * @author katana
 */
public class ConvertXMLUtils {
    public static final String CHARSET_UTF8 = "utf-8";            //utf-8编码
    public static final String CHARSET_GBK = "gbk";                //gbk编码

    /**
     * 将输入流转换为map集合
     *
     * @param input 内容为xml 格式的输入流,否则会出现未知异常
     *              参数示例值:
     *              <xml>
     *              <ToUserName><![CDATA[toUser]]></ToUserName>
     *              <FromUserName><![CDATA[fromUser]]></FromUserName>
     *              <CreateTime>1348831860</CreateTime>
     *              <MsgType><![CDATA[text]]></MsgType>
     *              <Content><![CDATA[this is a test]]></Content>
     *              <MsgId>1234567890123456</MsgId>
     *              </xml>
     * @return 返回示例值:
     * {MsgId=1234567890123456, FromUserName=fromUser, CreateTime=1348831860,
     * Content=this is a test, ToUserName=toUser, MsgType=text}
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> parseXml(InputStream input) throws DocumentException, IOException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = null;
        if (input != null) {
            map = new HashMap<String, String>();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(input);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList)
                map.put(e.getName(), e.getText());

            // 释放资源
            input.close();
            input = null;
        }
        return map;
    }

    /**
     * 将java对象解析为对应xml格式的字符串
     *
     * @param msg 待转换的对象,不包括 数组，集合, 仅限于类对象
     * @return xml 格式的字符串
     */

    public static String toXMLString(RespBasicMsg msg) {
        String result = "";
        if (msg != null) {
            XStream xs = XStreamFactory.init(true);
            xs.alias("xml", msg.getClass());
            result = xs.toXML(msg);
        }
        return result;
    }

    /**
     * 将输入流转换为字符串
     *
     * @param input 输入流
     * @return
     */
    private static String InputStreamToStr(InputStream input, String charset) {
        String result = "";
        int len = 0;
        byte[] array = new byte[1024];
        StringBuffer buffer = new StringBuffer();
        if (input != null) {
            try {
                while ((len = input.read(array)) != -1) {
                    buffer.append(new String(array, 0, len, charset));
                }
                result = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将输入流转换为utf-8码的字符串
     *
     * @param input
     * @return
     */
    public static String InputStreamToUTF8(InputStream input) {
        return InputStreamToStr(input, CHARSET_UTF8);
    }

    /**
     * 将输入流转换为gbk码的字符串
     *
     * @param input
     * @return
     */
    public static String InputStreamToGBK(InputStream input) {
        return InputStreamToStr(input, CHARSET_GBK);
    }

    /**
     * 将xml格式的数据转换为输入
     *
     * @param xmlData  xml格式的数据
     * @param response 待转换的类
     * @return 如果如果该输入流中不是xml格式的数据, 则会返回null, 使用时一定要判断是否为空g
     */
    public static WechatResult xmlStrToJavaObject(String xmlData, UnifiedNoticeResponse response) {
        WechatResult result = new WechatResult();
        Object object = null;
        XStream xstream = XStreamFactory.init(true);
        xstream.alias("xml", response.getClass());
        boolean fig = true;
        try {
            object = xstream.fromXML(xmlData, response);
            result.setMsg(xmlData);
        } catch (Exception ex) {
            object = null;
            result.setMsg(ex.getMessage());
            fig = false;
        }
        result.setObj(object);
        result.setSuccess(fig);
        return result;
    }

    /**
     * 将java对象转换为
     *
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> String toXmlData(BaseXmlObj obj) {
        String result = "";
        XStream xstream = XStreamFactory.init(true);
        xstream.alias("xml", obj.getClass());
        result = xstream.toXML(obj);
        return result;
    }

    /**
     * 将java对象转换为xml文件,并去除 _ 应用场景是 去除实体中有_划线的实体, 默认会有两个_,调用该方法则会去除一个
     *
     * @param obj
     * @return
     */
    public static <T> String toXMLDataAndSplit(BaseXmlObj obj) {
        String result = "";
        XStream xstream = new XStream(new DomDriver(
                "UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        xstream.alias("xml", obj.getClass());
        result = xstream.toXML(obj);
        return result;
    }

    /**
     * 将内容为xml格式输入流转换为    java对象
     *
     * @param input    输入流
     * @param response
     * @param charset
     * @return
     */
    public static <T> WechatResult InputStreamToJavaObject(InputStream input, UnifiedNoticeResponse response, String charset) {
        System.out.println("ConvertXMLUtils InputStreamToJavaObject------>");
        WechatResult wechatResult = new WechatResult();
        if (input != null) {
            System.out.println("ConvertXMLUtils InputStreamToJavaObject------>");
            String result = ConvertXMLUtils.InputStreamToStr(input, charset);
            System.out.println("result--->" + result);
            if (result != null && !"".equals(result)) {
                wechatResult = xmlStrToJavaObject(result, response);
            }
        }
        return wechatResult;
    }

    /**
     * 将xml数据映射到java对象中
     *
     * @param xml    待转换的xml格式的数据
     * @param tClass 待转换为的java对象
     * @return
     */
    public static Object getObjectFromXML(String xml, Class tClass) {
        //将从API返回的XML数据映射到Java对象
        XStream xStreamForResponseData = XStreamFactory.init(true);
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
        return xStreamForResponseData.fromXML(xml);
    }

//    /**
//     * 将有集合xml节点数据映射成为java对象
//     * @param xml
//     * @param tClass
//     * @return
//     */
//    public static Object getObjectFromCollectionXML(String xml, Class tClass,Class t) {
//        //将从API返回的XML数据映射到Java对象
//        XStream xStreamForResponseData = XStreamFactory.init(true);
//        xStreamForResponseData.alias("xml", tClass);
//        xStreamForResponseData.addImplicitCollection(tClass,"hblist","hbinfo",HbListItem.class);
//        xStreamForResponseData.useAttributeFor(tClass, "hblist");
//        xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
//        return xStreamForResponseData.fromXML(xml);
//    }

    //添加的
    private static class MyIntCoverter extends IntConverter {
        @Override
        public Object fromString(String str) {
            int value;
            try {
                value = (Integer) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyLongCoverter extends LongConverter {
        @Override
        public Object fromString(String str) {
            long value;
            try {
                value = (Long) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyFloatCoverter extends FloatConverter {
        @Override
        public Object fromString(String str) {
            float value;
            try {
                value = (Float) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }

    private static class MyDoubleCoverter extends DoubleConverter {
        @Override
        public Object fromString(String str) {
            double value;
            try {
                value = (Double) super.fromString(str);
            } catch (Exception e) {
                value = 0;
            }
            return value;
        }

        @Override
        public String toString(Object obj) {
            return super.toString(obj);
        }
    }
}
