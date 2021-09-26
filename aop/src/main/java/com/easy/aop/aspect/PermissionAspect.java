package com.easy.aop.aspect;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.easy.aop.AopManager;
import com.easy.aop.annotation.Permission;
import com.easy.aop.annotation.PermissionDenied;
import com.easy.aop.annotation.PermissionNoAskDenied;
import com.easy.aop.helper.permission.AopPermissionActivity;
import com.easy.aop.helper.permission.IPermission;
import com.easy.aop.utils.log.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
public class PermissionAspect {

    @Around("execution(@com.easy.aop.annotation.Permission * *(..)) && @annotation(permission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable {
        Context context;
        final Object object = joinPoint.getThis();
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else {
            //获取切入点方法上的参数列表
            Object[] objects = joinPoint.getArgs();
            if (objects.length > 0) {
                //非静态方法且第一个参数为context
                if (objects[0] instanceof Context) {
                    context = (Context) objects[0];
                } else {
                    //没有传入context 默认使用application
                    context = AopManager.Companion.getInstance().getApplication();
                }
            } else {
                context = AopManager.Companion.getInstance().getApplication();
            }
        }

        if (permission == null) {
            joinPoint.proceed();
            return;
        }

        AopPermissionActivity.PermissionRequest(context, permission.value(),
                permission.requestCode(), permission.rationale(), new IPermission() {
                    @Override
                    public void permissionGranted() {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                            log.log("permissionAspect", "permissionGranted error : " + throwable.getMessage());
                        }
                    }

                    @Override
                    public void permissionNoAskDenied(int requestCode, List<String> denyNoAskList) {
                        Class<?> cls = object.getClass();
                        Method[] methods = cls.getDeclaredMethods();
                        if (methods.length == 0) return;
                        for (Method method : methods) {
                            //过滤不含自定义注解PermissionNoAskDenied的方法
                            boolean isHasAnnotation = method.isAnnotationPresent(PermissionNoAskDenied.class);
                            if (isHasAnnotation) {
                                method.setAccessible(true);
                                //获取方法类型
                                Class<?>[] types = method.getParameterTypes();
                                if (types.length != 2) return;
                                //获取方法上的注解
                                PermissionNoAskDenied aInfo = method.getAnnotation(PermissionNoAskDenied.class);
                                if (aInfo == null) return;
                                //解析注解上对应的信息
                                try {
                                    method.invoke(object, requestCode, denyNoAskList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.log("permissionAspect", "permissionNoAskDenied error : " + e.getMessage());
                                }
                            }
                        }
                    }

                    @Override
                    public void permissionDenied(int requestCode, List<String> denyList) {
                        Class<?> cls = object.getClass();
                        Method[] methods = cls.getDeclaredMethods();
                        if (methods.length == 0) return;
                        for (Method method : methods) {
                            //过滤不含自定义注解PermissionDenied的方法
                            boolean isHasAnnotation = method.isAnnotationPresent(PermissionDenied.class);
                            if (isHasAnnotation) {
                                method.setAccessible(true);
                                //获取方法类型
                                Class<?>[] types = method.getParameterTypes();
                                if (types.length != 2) return;
                                //获取方法上的注解
                                PermissionDenied aInfo = method.getAnnotation(PermissionDenied.class);
                                if (aInfo == null) return;
                                //解析注解上对应的信息
                                try {
                                    method.invoke(object, requestCode, denyList);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    log.log("permissionAspect", "permissionDenied error : " + e.getMessage());
                                }
                            }
                        }
                    }

                });


    }
}
