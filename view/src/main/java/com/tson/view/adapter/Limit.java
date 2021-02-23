package com.tson.view.adapter;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date 2021/2/20 4:18 PM
 *
 * @author Tson
 */
public class Limit {

    @IntDef(value = {BaseAdapter.LOADING, BaseAdapter.LOAD_COMPLETE, BaseAdapter.LOAD_NO_MORE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SIM {
    }
}
