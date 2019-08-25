package com.katana.wx.common.entity.event;

import lombok.Data;

/**
 * 关注事件
 *
 * @author katana
 */
@Data
public class SubscribeEvent extends BasicEvent {
    private String EventKey; // 事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String Ticket; // 二维码的ticket，可用来换取二维码图片

    @Override
    public String setEvent() {
        return "subscribe";
    }

}
