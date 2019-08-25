package com.katana.wx.common.entity.resp;

import com.katana.wx.common.entity.resp.bean.Image;

/**
 * 回复图片消息
 *
 * @author katana
 */
public class RespImgMsg extends RespBasicMsg {
    private Image image;

    @Override
    public String setMsgType() {
        return "image";
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
