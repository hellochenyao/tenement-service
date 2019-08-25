package com.katana.tenement.web.manager.controller;

import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.web.manager.api.userinfo.RequestManagerUserInfoGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户 后台管理相关接口
 * <p>
 * Created by mumu on 2019/4/18.
 */
@RestController
@Api(tags = {"manager--用户相关接口"})
@RequestMapping("/manager/{admin}/user")
public class ManagerUserController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "用户列表")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public void users(RequestManagerUserInfoGet request) {


    }


    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public void modify(@PathVariable int userId, @RequestBody RequestManagerUserInfoGet request) {


    }


}
