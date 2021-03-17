package com.tson.utils.lib.download.utils.log

import android.util.Log

/**
 * Created tangxin
 * Time 2018/11/1 9:59 AM
 * @author tangxin
 */
internal class LogUtils {

    companion object {
        var openLog = false

        fun d(tag: String, msg: String) {
            if (openLog) {
                Log.d(tag, "d: $msg")
            }
        }

        fun i(tag: String, msg: String) {
            if (openLog) {
                Log.i(tag, "i: $msg")
            }
        }

        fun w(tag: String, msg: String) {
            if (openLog) {
                Log.w(tag, "w: $msg")
            }
        }

        fun e(tag: String, msg: String) {
            Log.e(tag, "e: $msg")
        }
    }

}
