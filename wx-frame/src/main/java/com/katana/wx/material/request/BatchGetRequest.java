package com.katana.wx.material.request;

/**
 * Created by mumu on 2017/12/12.
 */
public class BatchGetRequest {

    //素材的类型
    private String type;
    //从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
    private Integer offset;
    //返回素材的数量，取值在1到20之间
    private Integer count;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
