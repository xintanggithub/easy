package com.tson.easy.activity;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date 2021/2/20 4:18 PM
 *
 * @author Tson
 */
public class Limit {

    public static final int LAYOUT_ID = 1;
    public static final int METHOD = 2;

    @IntDef(value = {LAYOUT_ID, METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BindModelType {
    }

    public static final int PROJECT = 1;
    public static final int PUBLIC = 2;

    @IntDef(value = {PROJECT, PUBLIC})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewModelType {
    }

}
