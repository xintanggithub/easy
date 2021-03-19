package com.tson.log

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.util.Log

/**
 *  Date 2020-09-07 18:41
 *
 * @author Tson
 */
class LogUtils {
    companion object {
        /**
         * 日志输出级别NONE
         */
        private const val LEVEL_NONE = 0

        /**
         * 日志输出级别E
         */
        private const val LEVEL_ERROR = 1

        /**
         * 日志输出级别W
         */
        private const val LEVEL_WARN = 2

        /**
         * 日志输出级别I
         */
        private const val LEVEL_INFO = 3

        /**
         * 日志输出级别D
         */
        private const val LEVEL_DEBUG = 4

        /**
         * 日志输出级别V
         */
        private const val LEVEL_VERBOSE = 5

        /**
         * 是否允许输出log
         */
        private var mDebuggable = LEVEL_VERBOSE

        private val mTag = "TS "

        private var buildType = ""

        private var isSaveLog = false

        private lateinit var context: Context

        @SuppressLint("UseSparseArrays")
        private val listenerMap = HashMap<Int, LogListener>()

        fun getCtx(): Context {
            if (this::context.isInitialized) {
                return context
            }
            throw Exception("context is not  initialized")
        }

        fun logcatStatus() = isSaveLog

        fun openLogcat() {
            isSaveLog = true
        }

        fun closeLogcat() {
            isSaveLog = false
        }

        fun addListener(logListener: LogListener): Int {
            val hasCode = logListener.hashCode()
            listenerMap[hasCode] = logListener
            return hasCode
        }

        fun remove(hasCode: Int) {
            if (listenerMap.containsKey(hasCode)) {
                listenerMap.remove(hasCode)
            }
        }

        fun callback(lv: String, tag: String, content: String) {
            for (mutableEntry in listenerMap) {
                mutableEntry.value.log(lv, tag, content)
            }
            if (isSaveLog) {
                LogAsync(
                    LogFileUtils.stampToDate(
                        System.currentTimeMillis(),
                        "yyyy-MM-dd HH:mm:ss SSS"
                    ) + "  " + lv + "  " + tag + "  :" + content + "  <br/>"
                ).execute()
            }
        }

        internal class LogAsync(private val content: String) : AsyncTask<String, String, String>() {

            override fun doInBackground(vararg strings: String): String? {
                LogFileUtils.writeTxtToFile(content)
                return null
            }
        }

        /**
         * 设置调试Log开关
         *
         * @param isEnable 是否允许log
         */
        fun setDebuggable(isEnable: Boolean, progress: Context) {
            mDebuggable = if (isEnable) LEVEL_VERBOSE else LEVEL_NONE
            this.context = progress
        }

        fun setVersionName(buildType: String) {
            this.buildType = buildType
        }

        fun v(tag: String, msg: String) = v("$tag $msg")
        fun d(tag: String, msg: String) = d("$tag $msg")
        fun i(tag: String, msg: String) = i("$tag $msg")
        fun w(tag: String, msg: String) = w("$tag $msg")
        fun e(tag: String, msg: String) = e("$tag $msg")

        /**
         * 以级别为 d 的形式输出LOG
         */
        fun v(msg: String) {
            if (mDebuggable >= LEVEL_VERBOSE) {
                val tag = mTag + "v" + buildType + " "
                callback("verbose", tag, msg)
                Log.v(tag, msg)
            }
        }

        /**
         * 以级别为 d 的形式输出LOG
         */
        fun d(msg: String) {
            if (mDebuggable >= LEVEL_DEBUG) {
                val tag = mTag + "v" + buildType + " "
                callback("debug", tag, msg)
                Log.d(tag, msg)
            }
        }

        /**
         * 以级别为 i 的形式输出LOG
         */
        fun i(msg: String) {
            if (mDebuggable >= LEVEL_INFO) {
                val tag = mTag + "v" + buildType + " "
                callback("info", tag, msg)
                Log.i(tag, msg)
            }
        }

        /**
         * 以级别为 w 的形式输出LOG
         */
        fun w(msg: String) {
            if (mDebuggable >= LEVEL_WARN) {
                val tag = mTag + "v" + buildType + " "
                callback("warn", tag, msg)
                Log.w(tag, msg)
            }
        }

        fun force(msg: String) {
            val tag = mTag + "v" + buildType + " "
            callback("warn", tag, msg)
            Log.w(tag, msg)
        }

        /**
         * 以级别为 e 的形式输出LOG
         */
        fun e(msg: String) {
            if (mDebuggable >= LEVEL_ERROR) {
                val tag = mTag + "v" + buildType + " "
                callback("error", tag, msg)
                Log.e(tag, msg)
            }
        }

    }
}