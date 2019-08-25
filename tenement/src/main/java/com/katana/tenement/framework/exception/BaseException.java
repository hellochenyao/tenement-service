package com.katana.tenement.framework.exception;

/**
 * 异常基类
 */
public class BaseException extends RuntimeException {

    private String code;

    private String msg;

    protected BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("code: ").append(this.code)
                .append(" msg: ").append(this.msg);
        return stringBuilder.toString();
    }
}
