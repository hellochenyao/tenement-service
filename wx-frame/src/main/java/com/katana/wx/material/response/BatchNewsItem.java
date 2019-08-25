package com.katana.wx.material.response;

import com.katana.wx.material.entity.BasicItem;

import java.util.List;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchNewsItem extends BasicItem {


    private List<GetNewsResponse> content;

    private String update_time;

    public List<GetNewsResponse> getContent() {
        return content;
    }

    public void setContent(List<GetNewsResponse> content) {
        this.content = content;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
