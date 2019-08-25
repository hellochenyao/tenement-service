package com.katana.wx.user.request;

import lombok.Data;

import java.util.List;

/**
 * 批量获取用户基本信息请求参数
 *
 * @author katana
 */
@Data
public class BatchGetRequest {
    private List<BatchGet> user_list;
}
