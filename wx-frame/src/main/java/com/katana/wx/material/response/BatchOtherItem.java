package com.katana.wx.material.response;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchOtherItem extends BatchGetResponse {

    private String name;
    private String update_time;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
