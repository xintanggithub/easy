package com.easy.aop.aspect;

import android.util.Log;

import com.easy.aop.callback.DoBlock;
import com.easy.aop.helper.ThreadHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LockAspect {

    @Pointcut("execution(@com.easy.aop.annotation.Lock * *(..))")
    public void lockAspectMethod() {
    }

    @Around("lockAspectMethod()")
    public Object aroundRunAspectMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        ThreadHelper.INSTANCE.runWithLock(new DoBlock() {
            @Override
            public void doBlock() {
                try {
                    long start = System.currentTimeMillis();
                    joinPoint.proceed();
                    Log.d("LockAspect", "proceed done, use time = " + (System.currentTimeMillis() - start) + " ms");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        return "";
    }

}
