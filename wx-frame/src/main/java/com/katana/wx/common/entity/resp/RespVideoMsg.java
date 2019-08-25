package com.katana.wx.common.entity.resp;

import com.katana.wx.common.entity.resp.bean.Video;

public class RespVideoMsg extends RespBasicMsg {
    private Video video;

    @Override
    public String setMsgType() {
        return "video";
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
