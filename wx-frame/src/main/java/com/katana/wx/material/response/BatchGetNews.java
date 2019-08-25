package com.katana.wx.material.response;

import java.util.List;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchGetNews extends BatchGetResponse {

    private List<BatchNewsItem> item;


    public List<BatchNewsItem> getItem() {
        return item;
    }

    public void setItem(List<BatchNewsItem> item) {
        this.item = item;
    }
}
