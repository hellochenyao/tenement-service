package com.katana.tenement.framework.exception;

/**
 * 业务异常，http status：400
 */
public class BusinessException extends BaseException {

    public BusinessException(String code, String msg) {
        super(code, msg);
    }

    public BusinessException(Option option) {
        super(option.getCode(), option.getMsg());
    }

    public enum Option {

        //不存在用户
        NO_SUCH_USER("NO_SUCH_USER", "不存在该用户!");

        private String code;
        private String msg;

        Option(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
