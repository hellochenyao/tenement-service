package com.katana.tenement.web.app.controller;

import com.katana.tenement.dao.app.vo.userinfo.UserInfoVo;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.userinfo.UserModifyBo;
import com.katana.tenement.web.app.api.userinfo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by mumu on 2019/4/10.
 */
@RestController
@Api(tags = {"App--用户信息相关接口"})
@RequestMapping("/app/user/{userId}")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "获取用户详细信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseUserInfoGet info(@PathVariable int userId) {

        ResponseUserInfoGet response = new ResponseUserInfoGet();
        UserInfoVo userInfoVo = userInfoService.info(userId);
        BeanUtils.copyProperties(userInfoVo, response);

        return response;

    }

    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public void modify(@RequestBody RequestUserInfoPut request) {

        UserModifyBo userModifyBo = new UserModifyBo();
        BeanUtils.copyProperties(request,userModifyBo);
        userInfoService.modify(userModifyBo);
    }


    @ApiOperation(value = "解析微信用户信息并保存到数据库中")
    @RequestMapping(value = "/wx/info", method = RequestMethod.PUT)
    public ResponseUserInfoPut info(@RequestBody RequestWxUserInfoPut request) {

        ResponseUserInfoPut response = new ResponseUserInfoPut();
        UserInfoVo userInfoVo = userInfoService.wxInfo(request.getEncryptedData(), request.getIv());
        BeanUtils.copyProperties(userInfoVo, response);

        return response;

    }

    @ApiOperation(value = "解析微信用户电话信息并保存到数据库中")
    @RequestMapping(value = "/wx/phone", method = RequestMethod.PUT)
    public void phone(@RequestBody RequestWxUserInfoPut request) {

        userInfoService.wxPhoneNum(request.getEncryptedData(), request.getIv());
    }

    @ApiOperation(value="更新用户什么时候来过")
    @RequestMapping(value = "/wx/last/login", method = RequestMethod.PUT)
    public void updateLastLoginTime(@PathVariable("userId") int id){
        userInfoService.updateLastLoginTime(id);
    }
}