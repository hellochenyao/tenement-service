package com.katana.wx.menu.entity;

/**
 * 拍照或者相册发图
 *
 * @author katana
 */
public class PicphotoOralbum extends BasicMenu {
    private String key;
    private String sub_button[] = new String[]{};
    private String type = Menu.PIC_PHOTO_OR_ALBUM;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getSub_button() {
        return sub_button;
    }
}
