package com.katana.wx.material.response;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchGetResponse {

    private Integer total_count;

    private Integer item_count;

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }
}
