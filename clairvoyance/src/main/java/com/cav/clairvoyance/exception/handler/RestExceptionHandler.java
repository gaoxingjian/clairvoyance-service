package com.cav.clairvoyance.exception.handler;


import com.cav.clairvoyance.exception.BusinessException;
import com.cav.clairvoyance.exception.code.BaseResponseCode;
import com.cav.clairvoyance.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    /**
     * 系统繁忙，请稍后再试
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
     * @param e
     * @param <T>
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    <T> DataResult<T> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException, exception: {}", e);
        return new DataResult<>(e.getMessageCode(), e.getDetailMessage());
    }



}
