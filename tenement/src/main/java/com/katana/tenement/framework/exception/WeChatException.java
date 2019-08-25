package com.katana.tenement.framework.exception;

/**
 * @author katana
 * @date 2017/11/9
 */
public class WeChatException extends BaseException {

    public WeChatException() {
        super("40100", "请求微信异常！");
    }

    public WeChatException(String msg) {
        super("40100", msg);
    }

}
