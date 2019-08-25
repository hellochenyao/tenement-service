package com.katana.tenement.web.main.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.framework.dto.jwt.JwtDataDto;
import com.katana.tenement.framework.exception.InsufficientAuthException;
import com.katana.tenement.framework.util.JwtUtils;
import com.katana.tenement.service.app.bo.JwtStorageBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by mumu on 2019/3/11.
 */
public class WxLoginJwtInterception extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("Authorization");
        Map attribute = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userId = (String) attribute.get("userId");
        if (StringUtils.isEmpty(userId) || userId.equals("undefined")) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.NO_AUTH_LOGIN);
        }

        if (StringUtils.isEmpty(authorization)) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_AUTHORIZATION);
        }

        JwtDataDto jwtDataDto = JwtUtils.parseToken(authorization);
        if (jwtDataDto == null) {
            //token过期
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.AUTH_TOKEN_TIME_OUT);
        }

        if (StringUtils.isEmpty(jwtDataDto.getInfoStr())) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN);
        }

        JwtStorageBo jwtStorageBo = JSONObject.parseObject(jwtDataDto.getInfoStr(), JwtStorageBo.class);

        if (jwtStorageBo.getUserId() == 0) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.NO_USER);
        }

        request.setAttribute("userInfo", jwtStorageBo);

        return true;
    }
}
