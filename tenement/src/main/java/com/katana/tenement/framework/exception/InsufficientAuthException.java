package com.katana.tenement.framework.exception;


/**
 * 授权权限不足异常，http status：401
 */
public class InsufficientAuthException extends BaseException {

    public InsufficientAuthException(CodeOption insufficientAuthCodeOption) {
        super(insufficientAuthCodeOption.getSubCode(), insufficientAuthCodeOption.getSubMsg());
    }

    public InsufficientAuthException(CodeOption insufficientAuthCodeOption, String subMsg) {
        super(insufficientAuthCodeOption.getSubCode(), subMsg);
    }

    public enum CodeOption {

        INVALID_AUTH_AUTHORIZATION("invalid-authorization", "请求头未传入Authorization"),

        /**
         * 访问令牌已过期
         */
        AUTH_TOKEN_TIME_OUT("invalid-auth-refreshtoken", "refreshToken过期，请重新登录"),

        /**
         * 访问令牌已过期
         */
        NO_AUTH_LOGIN("no-auth-login", "refreshToken过期，请授权登陆！"),

        /**
         * 访问令牌缺失
         */
        MISSING_AUTH_TOKEN("missing-auth-token", "token缺失，请重新登录"),
        /**
         * 无效的访问令牌
         */
        INVALID_AUTH_TOKEN("invalid-auth-token", "token过期"),

        /**
         * 不存在用户
         */
        NO_USER("invalid-user", "不存在该用户");


        private String subCode;
        private String subMsg;

        CodeOption(String subCode, String subMsg) {
            this.subCode = subCode;
            this.subMsg = subMsg;
        }

        public String getSubCode() {
            return subCode;
        }

        public String getSubMsg() {
            return subMsg;
        }

    }
}
