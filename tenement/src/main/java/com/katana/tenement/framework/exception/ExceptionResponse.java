package com.katana.tenement.framework.exception;

import lombok.Data;

import java.io.Serializable;


@Data
public class ExceptionResponse implements Serializable {

    protected static final long serialVersionUID = 1L;
    protected String code;
    protected String msg;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ExceptionResponse(String msg) {
        this.msg = msg;
    }

    public ExceptionResponse(BaseException exception) {
        this.code = exception.getCode();
        this.msg = exception.getMsg();
    }

    /**
     * 判断是否成功
     *
     * @return boolean
     */
    public boolean success() {
        return code.equals("0");
    }

    /**
     * 判断是否通用异常
     *
     * @return boolean
     */
    public boolean generalException() {
        return !code.equals("0") && !code.equals("40000");
    }

    /**
     * 判断是否业务异常
     *
     * @return
     */
    public boolean businessException() {
        return code.equals("40000");
    }

}
