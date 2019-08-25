package com.katana.tenement.web.manager.api.userinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 挑战营
 * <p>
 * Created by mumu on 2019/4/17.
 */
@Data
public class RequestManagerUserInfoGet {

    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty("用户昵称 -- 模糊查询")
    private String nickName;

    @ApiModelProperty("查询开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty("查询结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty("条数")
    private int pageSize;

    @ApiModelProperty("页数")
    private int pageNo;


}
