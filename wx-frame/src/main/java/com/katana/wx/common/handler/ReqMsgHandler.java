package com.katana.wx.common.handler;

import com.katana.wx.common.entity.req.BasicMsgUserInfo;
import com.katana.wx.common.entity.results.WechatResult;

import java.util.Map;


/**
 * 请求消息处理
 *
 * @author katana
 */
public interface ReqMsgHandler {

    /**
     * 用户发送的为文本消
     *
     * @param msg    基础消息
     * @param params 请求参数
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult textMsg(BasicMsgUserInfo msg, Map<String, String> params);


    /**
     * 链接消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult linkMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 默认执行的消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult defaultMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 音乐执行的消息
     *
     * @param msg    基础参数
     * @param params 请求参数
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult musicMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 图片消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult imageMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 地理位置消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult locationMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 语言消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult voiceMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 视频消息
     *
     * @param msg    消息基类
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult videoMsg(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 小视频消息
     *
     * @param msg
     * @param params
     * @return 返回需要该消息回复的xml格式类型的字符串
     */
    public WechatResult shortvideo(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 消息类型为空时的默认回复,(该方法可不实现 情况基本没有)
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult msgTypeIsNullDefaultReply(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 事件类型为空时的默认回复 	(MsgType为event Event==null的情况)
     * (该方法可不实现,)
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult eventTypeIsNullDefaultReply(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 用户关注时调用的方法
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult subscribe(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 取消关注时调用的方法
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult unsubscribe(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 用户已关注时的事件推送
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult scan(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 上报地理位置事件
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult eventLocation(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 点击菜单拉取消息时的事件推送 (自定义菜单的click在这里做响应)
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult eventClick(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 点击菜单跳转链接时的事件推送 (自定义菜单的view在这里做响应)
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult eventView(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 事件类型默认返回
     *
     * @param msg
     * @param params
     * @return
     */
    public WechatResult eventDefaultReply(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 客服创建回话 事件
     *
     * @param msg    消息基类
     * @param params 参数内容
     * @return
     */
    public WechatResult kfCreateSession(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 客服关闭回话事件
     *
     * @param msg    消息基类
     * @param params xml内容
     * @return
     */
    public WechatResult kfCloseSession(BasicMsgUserInfo msg, Map<String, String> params);

    /**
     * 客服转接回话事件
     *
     * @param msg    消息基类
     * @param params xml内容
     * @return
     */
    public WechatResult kfSwitchSession(BasicMsgUserInfo msg, Map<String, String> params);

}
