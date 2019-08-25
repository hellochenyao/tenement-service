package com.katana.tenement.web.manager.api.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseSystemGet {
    @ApiModelProperty("系统时间")
    private Long currentDate;
}
