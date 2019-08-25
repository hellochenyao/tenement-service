package com.katana.wx.auth.response;

import lombok.Data;

/**
 * 网页授权用户信息
 *
 * @author katana
 */
@Data
public class AuthUserInfo implements java.io.Serializable {
    private String openid;
    private String nickname;
    private String sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;
    private String privilege;

    public AuthUserInfo() {
        super();
    }

    public AuthUserInfo(String openid, String nickname, String sex,
                        String province, String city, String country, String headimgurl,
                        String unionid, String privilege) {
        super();
        this.openid = openid;
        this.nickname = nickname;
        this.sex = sex;
        this.province = province;
        this.city = city;
        this.country = country;
        this.headimgurl = headimgurl;
        this.unionid = unionid;
        this.privilege = privilege;
    }
}