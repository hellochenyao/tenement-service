package com.katana.tenement.web.app.api.userBrowsing;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RequestUserBrowsingFilterGet {
    private int userId;

    private int invitationId;

    @ApiModelProperty("是否只看回复过的")
    private Boolean isHaveResponse;

    private int pageNo = 1;

    private int pageSize = 10 ;
}
