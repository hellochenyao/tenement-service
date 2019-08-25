package com.katana.wx.menu.entity;

public class ViewLimitedMenu extends BasicMenu {

    private String type = Menu.VIEW_LIMITED;
    private String media_id;


    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
