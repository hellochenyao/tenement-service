package com.katana.wx.common.entity.event;

import lombok.Data;

/**
 * 自定义菜单点击事件
 *
 * @author katana
 */
@Data
public class ClickEvent extends BasicEvent {
    private String EventKey; // 事件KEY值，与自定义菜单接口中KEY值对应

    @Override
    public String setEvent() {
        return "CLICK";
    }

}
