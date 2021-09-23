package com.easy.aop.aspect;

import com.easy.aop.annotation.Run;
import com.easy.aop.callback.DoBlock;
import com.easy.aop.helper.ThreadHelper;
import com.easy.aop.utils.log.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import static com.easy.aop.enumerate.Statistics.IO;

@Aspect
public class RunAspect {

    @Pointcut("execution(@com.easy.aop.annotation.Run * *(..))")
    public void runAspectMethod() {
    }

    @Around("runAspectMethod()")
    public Object aroundRunAspectMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法参数
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法地址
        Method method = signature.getMethod();
        //获取地址内容，即名称
        final String methodName = method.getName();

        // 获取注解信息，包含参数
        Method method2 = joinPoint.getThis().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
        Run run = method2.getAnnotation(Run.class);
        int type = null != run ? run.type() : IO;
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
        String parameters = params.toString() + " }";
        log.log(methodName, "parameters = " + parameters);
        final ProceedingJoinPoint joinPoint2 = joinPoint;
        ThreadHelper.INSTANCE.runWithType(type, new DoBlock() {
            @Override
            public void doBlock() {
                try {
                    long startTime = System.currentTimeMillis();
                    joinPoint2.proceed();
                    log.log(methodName, "proceed done, use time = " + (System.currentTimeMillis() - startTime) + " ms");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return "";
    }

}
