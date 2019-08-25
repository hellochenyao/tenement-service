package com.katana.tenement.web.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.domain.entity.AdminUserEntity;
import com.katana.tenement.framework.common.RedisClient;
import com.katana.tenement.framework.dto.jwt.JwtDataDto;
import com.katana.tenement.framework.dto.jwt.JwtDto;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.util.JwtUtils;
import com.katana.tenement.framework.util.MD5Utils;
import com.katana.tenement.framework.util.SnowflakeIdWorker;
import com.katana.tenement.framework.util.VerifyCodeUtils;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import com.katana.tenement.service.manager.AdminUserService;
import com.katana.tenement.service.manager.bo.AdminUserBo;
import com.katana.tenement.web.app.api.userinfo.RequestRefreshTokenPost;
import com.katana.tenement.web.app.api.userinfo.ResponseJwtAndRefreshPost;
import com.katana.tenement.web.manager.api.adminUser.RequestLoginInfoGet;
import com.katana.tenement.web.manager.api.adminUser.ResponseAuthCodeGet;
import com.katana.tenement.web.manager.api.adminUser.ResponseLoginInfoGet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.lang.System.out;

@RestController
@Api(tags = "manager-管理端登录相关权限")
@RequestMapping("/manager/user/auth")
public class ManagerAuthController {

    public final static String CODE_KEY = "auth_code_";
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private RedisClient redisClient;

    @ApiOperation(value = "管理员登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseLoginInfoGet login(@RequestBody RequestLoginInfoGet request, HttpServletRequest httpServletRequest) {

        ResponseLoginInfoGet response = new ResponseLoginInfoGet();
        AdminUserBo adminUserBo = new AdminUserBo();
        BeanUtils.copyProperties(request, adminUserBo);
        AdminUserEntity userInfo = adminUserService.login(adminUserBo);
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo, response);
        } else {
            throw new BusinessException("ERROR_PASSWORD", "用户不存在 或  密码错误!");
        }

        JwtDto jwtVo = JwtUtils.generateJwt(new JwtDataDto(userInfo.getId(), "manager"));
        response.setJwt(jwtVo.getToken());
        BeanUtils.copyProperties(jwtVo, response);
        return response;
    }

    @ApiOperation(value = "用户刷新登陆权鉴", notes = "根据旧jwt和refreshToken生成新权鉴")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public ResponseJwtAndRefreshPost refreshToken(@RequestBody RequestRefreshTokenPost request) {

        ResponseJwtAndRefreshPost response = new ResponseJwtAndRefreshPost();
        int userId = JwtUtils.validateRefreshToken(request.getJwt(), request.getRefreshToken());
        JwtStorageBo sessionBo = new JwtStorageBo();
        sessionBo.setUserId(userId);
        JwtDto jwtVo = JwtUtils.generateJwt(new JwtDataDto(JSONObject.toJSONString(sessionBo)));
        response.setJwt((jwtVo).getToken());
        response.setRefreshToken(jwtVo.getRefreshToken());

        return response;
    }

    @ApiOperation(value = "获取验证码")
    @RequestMapping(value = "/authCode", method = RequestMethod.GET)
    public ResponseAuthCodeGet getAuthCode() {
        ResponseAuthCodeGet response = new ResponseAuthCodeGet();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            String code = VerifyCodeUtils.generateVerifyCode(4).toLowerCase();
            VerifyCodeUtils.outputImage(100, 40, outputStream, code);
            StringBuilder sb = new StringBuilder();
            sb.append("data:image/png;base64,");
            BASE64Encoder base64Encoder = new BASE64Encoder();
            String base64Img = base64Encoder.encode(outputStream.toByteArray()).replaceAll("[\\s*\t\n\r]", "");
            sb.append(base64Img);
            response.setAuthCode(sb.toString());
            out.println(snowflakeIdWorker);
            String uuid = MD5Utils.getMd5(String.valueOf(snowflakeIdWorker.nextValue()));
            response.setUuid(uuid);
            redisClient.set(CODE_KEY + uuid, code, 60);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
