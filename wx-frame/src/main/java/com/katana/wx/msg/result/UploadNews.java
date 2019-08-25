package com.katana.wx.msg.result;

import lombok.Data;

/**
 * 上传图文素材
 *
 * @author katana
 */
@Data
public class UploadNews {
    private String type; // 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），次数为news，即图文消息
    private String media_id; // 媒体文件/图文消息上传后获取的唯一标识
    private String created_at; // 媒体文件上传时间
}
