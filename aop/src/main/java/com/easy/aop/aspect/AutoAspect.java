package com.easy.aop.aspect;

import com.easy.aop.utils.log.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AutoAspect {

    @Pointcut("execution(@com.easy.aop.annotation.Auto * *(..))")
    public void autoAspectMethod() {
    }

    @Pointcut("execution(@com.easy.aop.annotation.Auto *.new(..))")
    public void autoConstructorAnnotated() {
    }

    @Around("autoAspectMethod() || autoConstructorAnnotated()")
    public Object autoAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.log("before", "");

        joinPoint.proceed();

        log.log("end", "");
        return "";
    }

}
