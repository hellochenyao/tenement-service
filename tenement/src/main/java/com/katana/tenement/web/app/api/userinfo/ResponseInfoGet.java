package com.katana.tenement.web.app.api.userinfo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * User: cy
 * Date: 2020/1/4
 */
@Data
public class ResponseInfoGet {

    private List<UserInfo> data;

    private Integer total;

    @Data
    public static class UserInfo{

        @ApiModelProperty("用户唯一id")
        private int id;

        @ApiModelProperty("头像")
        private String avatar;

        @ApiModelProperty("昵称")
        private String nickName;

        @ApiModelProperty("性别")
        private int gender;

        @ApiModelProperty("电话")
        private String phone;

        private String signature;

        @ApiModelProperty("微信账号")
        private String weChat;

        @ApiModelProperty("毕业学校")
        private String school;

        @ApiModelProperty("学历")
        private String eduBack;

        @ApiModelProperty("学生/工作")
        private String isWorker;

        @ApiModelProperty("城市")
        private String city;

        @ApiModelProperty("省份")
        private String province;

        @ApiModelProperty("国家")
        private String country;

        @ApiModelProperty("微信openId")
        private String openId;

        @ApiModelProperty("微信unionId")
        private String unionId;

        @ApiModelProperty("最后一次登陆时间")
        private String lastLoginTime;

        @ApiModelProperty("第一次登陆时间")
        private String createTime;
    }
}
