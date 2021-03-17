package com.tson.utils.lib.download.utils.sp

import android.content.Context

/**
 * Created tangxin
 * Time 2018/10/31 5:57 PM
 */
class SpHelper(private val context: Context) {

    /**
     * 进度缓存
     */
    var sp: SettingPreferencesFactory? = null
        get() = if (null == field) SettingPreferencesFactory(context) else field

    private val listenerFlag: Int
        get() = sp?.getIntValue(LISTENER_DOWNLOAD_KEY, 0) ?: 0

    var path: String
        get() = sp?.getStringValue(PATH_DOWNLOAD_KEY) ?: ""
        set(path) = sp?.setStringValue(PATH_DOWNLOAD_KEY, path) ?: Unit

    var retryCount: Int
        get() = sp?.getIntValue(RETRY_DOWNLOAD_KEY, 1) ?: 1
        set(retryCount) = sp?.setIntValue(RETRY_DOWNLOAD_KEY, retryCount) ?: Unit

    var maxThreadCount: Int
        get() = sp?.getIntValue(MAX_THREAD_COUNT_DOWNLOAD_KEY, 3) ?: 3
        set(maxThreadCount) = sp?.setIntValue(MAX_THREAD_COUNT_DOWNLOAD_KEY, maxThreadCount) ?: Unit

    var debugLog: Boolean
        get() = sp?.getBoolValue(OPEN_LOG_DOWNLOAD_KEY, false) ?: false
        set(isOpenLog) = sp?.setBoolValue(OPEN_LOG_DOWNLOAD_KEY, isOpenLog) ?: Unit

    fun setListenerFlag(): Int {
        var count = listenerFlag
        count++
        sp?.setIntValue(LISTENER_DOWNLOAD_KEY, count) ?: Unit
        return count
    }

    companion object {

        private val LISTENER_DOWNLOAD_KEY = "Tson.download.module.listener.key"
        private val PATH_DOWNLOAD_KEY = "Tson.download.module.path.key"
        private val RETRY_DOWNLOAD_KEY = "Tson.download.module.RETRY.key"
        private val MAX_THREAD_COUNT_DOWNLOAD_KEY = "Tson.download.module.maxThread.key"
        private val OPEN_LOG_DOWNLOAD_KEY = "Tson.download.nodule.maxThread.key"
    }

}
