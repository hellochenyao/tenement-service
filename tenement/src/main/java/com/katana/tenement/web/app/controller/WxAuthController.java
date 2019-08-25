package com.katana.tenement.web.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.framework.common.RedisClient;
import com.katana.tenement.framework.dto.jwt.JwtDataDto;
import com.katana.tenement.framework.dto.jwt.JwtDto;
import com.katana.tenement.framework.util.JwtUtils;
import com.katana.tenement.framework.util.VerifyCodeUtils;
import com.katana.tenement.service.app.UserInfoService;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import com.katana.tenement.service.app.bo.userinfo.UserLoginBo;
import com.katana.tenement.web.app.api.userinfo.RequestRefreshTokenPost;
import com.katana.tenement.web.app.api.userinfo.RequestWxLoginPost;
import com.katana.tenement.web.app.api.userinfo.ResponseJwtAndRefreshPost;
import com.katana.tenement.web.app.api.userinfo.ResponseLoginPost;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mumu on 2019/4/10.
 */
@RestController
@Api(tags = {"App--登陆权限相关接口"})
@RequestMapping("/app/wx-user/auth")
public class WxAuthController {

    private final static String CODE_KEY = "auth_code_";
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "小程序用户登陆")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseLoginPost findConstructionCompletion(@RequestBody RequestWxLoginPost request) {

        ResponseLoginPost response = new ResponseLoginPost();

        UserLoginBo userLoginBo = userInfoService.login(request.getJscode());
        JwtStorageBo sessionBo = new JwtStorageBo();
        BeanUtils.copyProperties(userLoginBo, sessionBo);
        //缓存jwtSecret
        JwtDto jwtDto = JwtUtils.generateJwt(new JwtDataDto(JSONObject.toJSONString(sessionBo)));
        BeanUtils.copyProperties(userLoginBo, response);

        response.setJwt(jwtDto.getToken());
        response.setRefreshToken(jwtDto.getRefreshToken());

        return response;
    }

    @ApiOperation(value = "用户刷新登陆权鉴", notes = "根据旧jwt和refreshToken生成新权鉴")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseJwtAndRefreshPost refreshToken(@RequestBody RequestRefreshTokenPost request) {

        ResponseJwtAndRefreshPost response = new ResponseJwtAndRefreshPost();
        JwtDataDto jwtDataDto = JwtUtils.parseToken(request.getJwt());
        JwtDto jwtVo = JwtUtils.generateJwt(jwtDataDto);
        response.setJwt((jwtVo).getToken());
        response.setRefreshToken(jwtVo.getRefreshToken());

        return response;
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/{uuid}/authcode", method = RequestMethod.GET)
    public void getAuthCode(@PathVariable(value = "uuid", required = true) String uuid, HttpServletResponse response) {
        if (StringUtils.isEmpty(uuid)) {
            return;
        }

        String haveCode = redisClient.get(CODE_KEY + uuid);
        if (StringUtils.isNotEmpty(haveCode)) {
            return;
        }

        String code = VerifyCodeUtils.generateVerifyCode(4).toLowerCase();
        redisClient.set(CODE_KEY + uuid, code, 60);
        try {
            response.setContentType("image/png");
            VerifyCodeUtils.outputImage(100, 40, response.getOutputStream(), code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
