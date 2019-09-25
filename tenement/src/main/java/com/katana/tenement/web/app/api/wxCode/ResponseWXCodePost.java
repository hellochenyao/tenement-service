package com.katana.tenement.web.app.api.wxCode;

import lombok.Data;

@Data
public class ResponseWXCodePost {
    private String codeBase;
    private String backUrl;
    private String headUrl;
}
