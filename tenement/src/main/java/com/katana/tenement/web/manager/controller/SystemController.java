package com.katana.tenement.web.manager.controller;

import com.katana.tenement.web.manager.api.system.ResponseSystemGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "系统相关")
@RequestMapping("/manager/system")
public class SystemController {

    @ApiOperation("获取系统时间")
    @RequestMapping(value = "/time", method = RequestMethod.GET)
    public ResponseSystemGet getCurrentSystemTime() {
        ResponseSystemGet response = new ResponseSystemGet();
        response.setCurrentDate(System.currentTimeMillis());
        return response;
    }
}
