package com.katana.wx.common.entity.resp.bean;

import java.util.List;

/**
 * 图文消息
 *
 * @author katana
 */
public class Articles {
    private List<Item> list;

    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

}
