package com.katana.tenement.framework.dto.jwt;

import lombok.Data;

/**
 * Created by mumu on 2019/3/27.
 */
@Data
public class JwtDto {

    private String token;

    private String refreshToken;

    public JwtDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

