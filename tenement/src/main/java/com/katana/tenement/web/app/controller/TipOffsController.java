package com.katana.tenement.web.app.controller;

import com.katana.tenement.service.app.TipOffsService;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.tipoffs.UserTipOffsBo;
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
@Api(tags = {"App--登陆权限相关接口"})
@RequestMapping("/app/tip-offs/{userId}")
public class TipOffsController {

    @Autowired
    private TipOffsService tipOffsService;


    @ApiOperation(value = "举报  用户/帖子")
    @RequestMapping(value = "/{type}", method = RequestMethod.POST)
    public void tipOffUser(@PathVariable int type ,@RequestBody RequestTipOffsPost request) {

        UserTipOffsBo userTipOffsBo = new UserTipOffsBo();
        BeanUtils.copyProperties(request,userTipOffsBo);
        userTipOffsBo.setType(type);
        tipOffsService.tipOffs(userTipOffsBo);
    }

}
