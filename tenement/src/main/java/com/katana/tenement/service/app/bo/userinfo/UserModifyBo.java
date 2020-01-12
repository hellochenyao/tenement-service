package com.katana.tenement.service.app.bo.userinfo;

import lombok.Data;


/**
 * Created by mumu on 2019/4/11.
 */
@Data
public class UserModifyBo {


    //性别
    private Integer gender;

    //微信账号
    private String weChat;

    //毕业学校
    private String school;

    //职业
    private String occupation;

    //学历
    private String eduBack;

    //学生/工作
    private Integer isWorker;

    //入学年级
    private Integer grade;

    private String nickName;

    private Integer userId;

    private String phone;

}
