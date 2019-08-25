package com.katana.wx.weapp.user.response;

import lombok.Data;


/**
 * Created by mumu on 2019/4/17.
 */
@Data
public class WxUserStep {

    private stepInfo stepInfoList;

    @Data
    public static class stepInfo {

        private int step;
        private String timestamp;

    }

}
