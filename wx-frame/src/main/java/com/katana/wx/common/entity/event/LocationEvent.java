package com.katana.wx.common.entity.event;

import lombok.Data;

/**
 * 地理位置信息
 *
 * @author katana
 */
@Data
public class LocationEvent extends BasicEvent {
    private double Latitude; // 地理位置纬度
    private double Longitude; // 地理位置经度
    private double Precision; // 地理位置精度

    @Override
    public String setEvent() {
        return "LOCATION";
    }

}
