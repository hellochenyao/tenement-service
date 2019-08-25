package com.katana.wx.user.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户openid集合
 *
 * @author katana
 */
@Data
public class UserOpenListInfo implements java.io.Serializable {
    private int total;
    private int count;
    private List<String> openid;
}
