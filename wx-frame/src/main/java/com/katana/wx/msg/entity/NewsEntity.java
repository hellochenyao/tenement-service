package com.katana.wx.msg.entity;

import lombok.Data;

/**
 * 图文消息的实体
 *
 * @author katana
 */
@Data
public class NewsEntity {
    private String thumb_media_id; // 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
    private String author; // 图文消息的作者
    private String title; // 图文消息的标题
    private String content_source_url; // 图文消息点击阅读原文的链接
    private String content; // 图文消息页面的内容，支持HTML标签
    private String digest; // 图文消息的描述
    private int show_conver_pic; // 是否显示为封面 1表示显示为封面 0 不显示为封面

}