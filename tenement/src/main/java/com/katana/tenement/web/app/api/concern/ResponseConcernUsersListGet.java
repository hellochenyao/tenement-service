package com.katana.tenement.web.app.api.concern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseConcernUsersListGet {

    private List<User> data;

    private int total;

    @Data
    public static class User{

        private int userid;

        @ApiModelProperty("头像")
        private String avatar;

        @ApiModelProperty("昵称")
        private String nickName;

        @ApiModelProperty("性别")
        private int gender;

        private String lastLoginTime;
    }
}
