package com.katana.wx.menu.entity;

/**
 * 链接菜单
 *
 * @author katana
 */
public class ViewMenu extends BasicMenu {
    private String url;
    private String type = Menu.VIEW;


    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
