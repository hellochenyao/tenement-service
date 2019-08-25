package com.katana.wx.common.entity.event;

import lombok.Data;

/**
 * 自定义菜单跳转链接
 *
 * @author katana
 */
@Data
public class ViewEvent extends BasicEvent {
    private String EventKey; // 事件KEY值，设置的跳转URL

    @Override
    public String setEvent() {
        return "VIEW";
    }
}
