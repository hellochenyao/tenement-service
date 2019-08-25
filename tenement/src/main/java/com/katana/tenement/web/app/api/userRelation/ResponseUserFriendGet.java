package com.katana.tenement.web.app.api.userRelation;

import lombok.Data;

import java.util.List;
@Data
public class ResponseUserFriendGet {

    private List<User> users;

    private int total;

    @Data
    public static class User{

        private int userid;

        private String avatar;

        //昵称
        private String nickName;

        //性别
        private int gender;

        //微信账号
        private String weChat;

        //毕业学校
        private String school;

        //年级
        private int grade;

        //职业
        private String occupation;

        //学历
        private String eduBack;

        //学生/工作
        private int isWorker;

        //电话
        private String phone;

        //位置
        private String location;

        //城市
        private String city;

        //省份
        private String province;

        //国家
        private String country;

        //最后一次登陆时间
        private String lastLoginTime;
    }
}
