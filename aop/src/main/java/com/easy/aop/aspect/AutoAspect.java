package com.easy.aop.aspect;

import com.easy.aop.AopManager;
import com.easy.aop.annotation.Auto;
import com.easy.aop.annotation.AutoParameter;
import com.easy.aop.callback.DoProceed;
import com.easy.aop.utils.log.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        final String methodName = method.getName();
        Method method2 = joinPoint.getThis().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        Auto run = method2.getAnnotation(Auto.class);
        final String action = null != run ? run.action() : "";
        AutoParameter[] autoParameters = null != run ? run.parameter() : null;
        final Map<String, String> parameter = new HashMap<>();
        if (null != autoParameters) {
            for (AutoParameter autoParameter : autoParameters) {
                parameter.put(autoParameter.key(), autoParameter.value());
            }
        }
        log.log(methodName, "action = " + action);
        log.log(methodName, "parameter = " + parameter);
        final ProceedingJoinPoint joinPoint2 = joinPoint;
        AopManager.Companion.getInstance().beforeCallback(action, parameter, new DoProceed() {
            @Override
            public void runMethod() {
                try {
                    long startTime = System.currentTimeMillis();
                    joinPoint2.proceed();
                    log.log(methodName, "proceed done, use time = " + (System.currentTimeMillis() - startTime));
                    AopManager.Companion.getInstance().afterCallback(action, parameter);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void unDo() {
                log.log(methodName, "proceed stop handle");
            }
        });
        return "";
    }

}
