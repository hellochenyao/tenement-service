package com.katana.wx.material.request;

import com.katana.wx.material.entity.NewsEntity;

import java.util.List;

/**
 * Created by mumu on 2017/12/12.
 */
public class UpdateNews {

    //要修改的图文消息的id
    private String media_id;

    //要更新的文章在图文消息中的位置（多图文消息时，此字段才有意义），第一篇为0
    private int index;

    private List<NewsEntity> articles;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<NewsEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsEntity> articles) {
        this.articles = articles;
    }
}
