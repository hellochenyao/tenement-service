package com.katana.wx.common.entity.results;

import java.io.Serializable;

/**
 * Created by mumu on 2017/12/12.
 */
public class MaterialResult extends ResultState implements Serializable {

    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
