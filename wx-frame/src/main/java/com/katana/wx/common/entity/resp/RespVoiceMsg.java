package com.katana.wx.common.entity.resp;

import com.katana.wx.common.entity.resp.bean.Voice;

/**
 * 回复语音消息
 *
 * @author katana
 */
public class RespVoiceMsg extends RespBasicMsg {
    private Voice voice;

    @Override
    public String setMsgType() {
        return "voice";
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
}
