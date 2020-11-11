package com.cav.clairvoyance.exception.handler;


import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍后再试
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(Exception.class)
    public <T> DataResult<T> handlerException(Exception e) {
        log.error("Exception, exception: {}", e);
        return DataResult.getResult(BaseResponseCode.SYSTEM_BUSY);
    }

    /**
     * 自定义全局异常处理
     *
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    <T> DataResult<T> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException, exception: {}", e);
        return new DataResult<>(e.getMessageCode(), e.getDetailMessage());
    }

    /**
     * 没有权限 返回403视图
     *
     * @param
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public <T> DataResult<T> erroPermission(AuthorizationException e) {
        log.error("BusinessException,exception:{}", e);
        return new DataResult<>(BaseResponseCode.UNAUTHORIZED_ERROR);

    }

    /**
     * 处理validation 框架异常
     *
     * @param e
     * @return com.hth.cloud.common.base.HgResult<T>
     * @throws
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    <T> DataResult<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("methodArgumentNotValidExceptionHandler bindingResult.allErrors():{},exception:{}", e.getBindingResult().getAllErrors(), e);
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return createValidExceptionResp(errors);
    }

    private <T> DataResult<T> createValidExceptionResp(List<ObjectError> errors) {
        String[] msgs = new String[errors.size()];
        int i = 0;
        for (ObjectError error : errors) {
            msgs[i] = error.getDefaultMessage();
            log.info("msg={}", msgs[i]);
            i++;
        }
        return DataResult.getResult(BaseResponseCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getCode(), msgs[0]);
    }


}
