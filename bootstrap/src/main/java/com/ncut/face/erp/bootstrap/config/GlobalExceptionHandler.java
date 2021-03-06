package com.ncut.face.erp.bootstrap.config;

import com.ncut.face.erp.service.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result handleRuntimeException(RuntimeException e) {
        log.error("GlobalExceptionHandler.handleRuntimeException==>", e);
        return Result.buildFailedRes(500, e.getMessage());
    }

    @ExceptionHandler(ShiroException.class)
    @ResponseBody
    public Result handleShiroException(ShiroException e) {
        return Result.buildFailedRes(500, "您无权访问此页面,请检查登陆状态或确认权限");
    }
}
