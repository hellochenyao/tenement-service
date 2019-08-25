package com.katana.wx.common.entity.resp;

import com.katana.wx.common.entity.resp.bean.Music;

public class RespMusicMsg extends RespBasicMsg {
    private Music music;

    @Override
    public String setMsgType() {
        return "music";
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

}
