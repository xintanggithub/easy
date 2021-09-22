package com.easy.aop.enumerate;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Statistics {
    /**
     * 线程类型
     */
    public static final int IO = 1;
    public static final int UI = 2;
    public static final int MAIN = 3;
    public static final int SINGLE = 5;
    public static final int FIXED = 6;
    public static final int CACHE = 7;


    @IntDef(value = {IO, UI, MAIN, SINGLE, FIXED, CACHE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RunType {
    }

}
