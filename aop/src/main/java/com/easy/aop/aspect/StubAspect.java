package com.easy.aop.aspect;

import com.easy.aop.annotation.Stub;
import com.easy.aop.utils.log.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class StubAspect {

    @Pointcut("execution(@com.easy.aop.annotation.Stub * *(..))")
    public void methodAnnotated() {
    }

    @Pointcut("execution(@com.easy.aop.annotation.Stub *.new(..))")
    public void constructorAnnotated() {
    }

    @Around("methodAnnotated() || constructorAnnotated()")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法地址
        Method method = signature.getMethod();
        //获取地址内容，即名称
        String methodName = method.getName();

        // 获取注解信息，包含参数
        Method method2 = joinPoint.getThis().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        Stub annotation = method2.getAnnotation(Stub.class);
        String tag = null != annotation ? annotation.tag() : "StubAspect";
        String content = null != annotation ? annotation.content() : " ";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        //对应放一起
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        String parameters = params.toString() + "}";

        log.log(tag, content);
        log.log(tag, "parameters = " + parameters);

        log.log(tag, "start proceed, method name = " + methodName);
        long startTime = System.currentTimeMillis();
        joinPoint.proceed();
        log.log(tag, "proceed done, use time = " + (System.currentTimeMillis() - startTime) + " ms");
        return "";
    }
}
