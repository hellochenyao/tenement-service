package com.katana.wx.material.response;

import java.util.List;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchGetOther extends BatchGetResponse {

    private List<BatchOtherItem> item;


    public List<BatchOtherItem> getItem() {
        return item;
    }

    public void setItem(List<BatchOtherItem> item) {
        this.item = item;
    }
}
