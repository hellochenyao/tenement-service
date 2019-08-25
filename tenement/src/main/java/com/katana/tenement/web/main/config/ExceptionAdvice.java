package com.katana.tenement.web.main.config;

import com.katana.tenement.framework.exception.BusinessException;
import com.katana.tenement.framework.exception.ExceptionResponse;
import com.katana.tenement.framework.exception.IllegalParameterException;
import com.katana.tenement.framework.exception.InsufficientAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常控制器增强
 *
 * @author katana
 * @date 2017/6/22
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionAdvice {

    /**
     * 系统内部异常(eg.空指针异常、强转异常等等)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse exception(Exception e) {
        e.printStackTrace();
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("UNKNOW-ERROR");
        response.setMsg("未知异常");
        return response;
    }

    /**
     * 业务类等异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse businessException(BusinessException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(e.getCode());
        response.setMsg(e.getMsg());
        return response;
    }

    /**
     * 非法参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse illegalParameterException(IllegalParameterException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(e.getCode());
        response.setMsg(e.getMsg());
        return response;
    }

    /**
     * 未授权异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InsufficientAuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse insufficientAuthException(InsufficientAuthException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode(e.getCode());
        response.setMsg(e.getMsg());
        return response;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notRequestBodyException(HttpMessageNotReadableException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("ILLEGAL_PARAMETER");
        response.setMsg("参数为空，请通过requestBody传参");
        return response;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notRequestApplicationJsonException(HttpMessageNotReadableException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("ILLEGAL_PARAMETER_EXCEPTION");
        response.setMsg("请设置请求头Content-Type为application/json;charset=UTF-8");
        return response;
    }

    /**
     * 不允许的请求方式
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionResponse httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setCode("ILLEGAL_REQUEST_METHOD");
        response.setMsg("请求方式错误");
        return response;
    }


}
