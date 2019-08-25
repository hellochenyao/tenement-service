package com.katana.wx.pay.request;


import lombok.Data;

/**
 * 下载对账单
 *
 * @author katana
 */
@Data
public class DownloadBillRequest extends BasicPayParams {

    private String device_info; // 设备号
    private String bill_date; // 对账单日期
    private String bill_type; // 账单类型

    public DownloadBillRequest() {
        super();
    }

    public DownloadBillRequest(String deviceInfo, String billDate,
                               String billType) {
        super();
        device_info = deviceInfo;
        bill_date = billDate;
        bill_type = billType;
    }

}
