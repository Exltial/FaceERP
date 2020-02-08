package com.ncut.face.erp.bootstrap.config;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.ncut.face.erp.service.common.Result;
import com.ncut.face.erp.service.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Slf4j
public class AspectConfig {
    @Value("#{'${aspect.white.path}'.split(',')}")
    List<String> whitePath;

    @Pointcut("execution(* com.ncut.face.erp.bootstrap.controller.api..*.*(..))")
    public void pointCut() {
        //do nothing here
    }

    @Around("pointCut()")
    public Object handleMethod(ProceedingJoinPoint joinPoint) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Result<?> result;
        try {
            if (!whitePath.contains(joinPoint.getSignature().getDeclaringType().getName())) {
                log.info("method:[{}]==>begin:{}", joinPoint.getSignature(), JSON.toJSONString(Lists.newArrayList(joinPoint.getArgs())));
            }
            result = (Result<?>) joinPoint.proceed(joinPoint.getArgs());
            if (!whitePath.contains(joinPoint.getSignature().getDeclaringType().getName())) {
                log.info("method:[{}]==>end:{} ", joinPoint.getSignature(), JSON.toJSON(result));
            }
            log.info("cost:[{}ms]", stopwatch.stop().elapsed(TimeUnit.MILLISECONDS));
        } catch (Throwable throwable) {
            log.error("{} throws an exception==>e:{}", joinPoint.getSignature(), throwable);
            result = handlerException(throwable);
        }
        return result;
    }

    private Result<?> handlerException(Throwable e) {
        Result<?> result;
        if (e.getClass().isAssignableFrom(BaseException.class)) {
            BaseException exception = (BaseException) e;
            result = Result.buildFailedRes(501, exception.getMessage());
        } else {
            result = Result.buildFailedRes(500, "系统异常,请联系管理员");
        }
        return result;
    }
}
