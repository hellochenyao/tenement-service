package com.katana.tenement.service.app.impl;

import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.service.app.WxAuthService;
import com.katana.wx.common.entity.results.WechatResult;
import com.katana.wx.weapp.user.conn.WeappUserConn;
import com.katana.wx.weapp.user.response.SessionKeyResponse;
import org.springframework.stereotype.Service;

/**
 * Created by mumu on 2019/3/27.
 */
@Service
public class WxAuthServiceImpl implements WxAuthService {


    /**
     * 登录凭证校验
     *
     * @param appid
     * @param secret
     * @param jsCode
     * @return
     */
    @Override
    public SessionKeyResponse getSessionKey(String appid, String secret, String jsCode) {

        WechatResult wechatResult = WeappUserConn.jscode2session(appid, secret, jsCode);
        //失败
        if (!wechatResult.isSuccess()) {
            throw new BusinessException("WE_CHART_ERROR", "微信登陆校验失败！");
        }
        SessionKeyResponse sessionKeyResponse = (SessionKeyResponse) wechatResult.getObj();

        return sessionKeyResponse;
    }
}
