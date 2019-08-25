package com.katana.wx.material.response;

import com.katana.wx.material.entity.NewsItem;

import java.util.List;

/**
 * Created by mumu on 2017/12/12.
 */
public class GetNewsResponse {

    private List<NewsItem> news_item;

    public List<NewsItem> getNews_item() {
        return news_item;
    }

    public void setNews_item(List<NewsItem> news_item) {
        this.news_item = news_item;
    }
}
