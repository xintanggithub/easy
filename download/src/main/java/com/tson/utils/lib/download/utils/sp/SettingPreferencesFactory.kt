package com.tson.utils.lib.download.utils.sp

import android.content.Context

/**
 * Created tangxin
 * Time 2018/10/11 下午1:47
 */
class SettingPreferencesFactory(context: Context) : BaseSharedPreferencesFactory(context) {

    override val key: String
        get() = "market_lib_sp"

    companion object {
        private val TAG = "SettingPreferencesFactory"
    }
}
