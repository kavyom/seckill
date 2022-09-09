package com.test.seckill.exception;

import com.test.seckill.base.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(BusinessException.class)
    public BaseResult handleBusinessException(BusinessException e) {
        BaseResult r = new BaseResult();
        r.setCode(e.getCode());
        r.setMsg(e.getMsg());

        return r;
    }

    @ExceptionHandler(Exception.class)
    public BaseResult handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return BaseResult.error();
    }
}
