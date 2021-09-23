package com.easy.aop.utils.log;

import android.util.Log;

public class log {
    public static void log(String tag, String content) {
        Log.w(tag, content);
        AopLogHelper.Companion.getInstance().logCallback(tag, content);
    }
}
