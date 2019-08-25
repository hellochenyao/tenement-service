package com.katana.tenement.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.katana.tenement.framework.dto.jwt.JwtDataDto;
import com.katana.tenement.framework.dto.jwt.JwtDto;
import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.exception.InsufficientAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * JWT帮助类
 *
 * @author admin
 * @date 2018/4/28
 */
@Slf4j
@Component("JwtUtils")
public class JwtUtils {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    //jwt保存时间1周
    private static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;
    //密钥，颁发jwt和校验时需要用到
    private static final String SECRET = "cwerewrjzcfjlasmnclwerjxzmasmdnwejdasmc";

    /**
     * 生成token，和refreshToken
     *
     * @param tokenVo
     * @return
     */
    public synchronized static JwtDto generateJwt(JwtDataDto tokenVo) {
        HashMap<String, Object> map = tokenVo.toMap();
        //创建jwt
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        //jwt前面一般都会加Bearer
        return new JwtDto(TOKEN_PREFIX + jwt, MD5Utils.getMd5(jwt + SECRET));
    }


    private static String getSecretFromJwt(String jwt) {
        String secret = "";
        try {
            String[] tokenParts = jwt.split("\\.");
            if (tokenParts.length < 2) {
                throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN);
            }
            String playload = tokenParts[1];
            String info = new String(Base64.getDecoder().decode(playload), "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(info);
            Integer userId = (Integer) jsonObject.get("userId");

            secret = SECRET;
        } catch (UnsupportedEncodingException e) {
            // TODO
        } catch (NullPointerException e) {
            throw new BusinessException("USER-NOT-FOUND", "用户不存在");
        }
        return secret;
    }


    /**
     * 解析token
     *
     * @param token
     * @return 当返回值为null，表示token过期
     */
    public static JwtDataDto parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.MISSING_AUTH_TOKEN);
        }
        // 解析token.
        try {
            // 分离secret
            String secret = getSecretFromJwt(token);
            Map<String, Object> body = Jwts.parser()
//                    .setSigningKey(SECRET)
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            //token添加字段的话，这边要相应的解析
            JwtDataDto JwtDataDto = new JwtDataDto(body);

            return JwtDataDto;
        } catch (Exception e) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN);
        }
    }

    /**
     * 校验token
     *
     * @param token
     */
    public static void validateToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.MISSING_AUTH_TOKEN);
        }
        Claims claims = analysisJwt(token.replace(TOKEN_PREFIX, ""));
        if (claims == null) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN);
        }
        Long expTime = ((Integer) claims.get("exp")).longValue();
        long nowTime = System.currentTimeMillis() / 1000;
        if (expTime - nowTime <= 0) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.AUTH_TOKEN_TIME_OUT);
        }
    }

    /**
     * 校验refreshToken
     *
     * @param jwt
     * @param refreshToken
     * @return 用户id
     */
    public static Integer validateRefreshToken(String jwt, String refreshToken) {
        jwt = jwt.replace(TOKEN_PREFIX, "");

        if (StringUtils.isEmpty(jwt) || !MD5Utils.getMd5(jwt + SECRET).equals(refreshToken)) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN, "Error jwt");
        }

        Claims claims = analysisJwt(jwt);
        if (claims == null) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.INVALID_AUTH_TOKEN);
        }

        Long expTime = ((Integer) claims.get("exp")).longValue();
        long nowTime = System.currentTimeMillis() / 1000;

        long days = (nowTime - expTime) / (60 * 60 * 24) + 1;
        if (days > 7) {
            throw new InsufficientAuthException(InsufficientAuthException.CodeOption.AUTH_TOKEN_TIME_OUT);
        }

        return (Integer) claims.get("userId");
    }

    /**
     * 生成token
     *
     * @param jwt
     * @return
     */
    public static Claims analysisJwt(String jwt) {
        // 分离secret
        String secret = getSecretFromJwt(jwt);
        // 解析token.
        try {
            Claims body = Jwts.parser()
//                    .setSigningKey(SECRET)
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
            return body;
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    /**
     * HttpServletRequest包装类
     */
    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

        public Map<String, String> getClaims() {
            return claims;
        }
    }

}

