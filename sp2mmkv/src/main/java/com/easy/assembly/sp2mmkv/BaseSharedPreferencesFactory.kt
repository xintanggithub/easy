package com.easy.assembly.sp2mmkv

import android.content.SharedPreferences
import com.tencent.mmkv.MMKV

/**
 * @author Tson
 */
class BaseSharedPreferencesFactory {

    companion object {
        const val M_MKV_M_MAP_ID = "blog.android.cache.key" // mmkv 存储key
    }

    private lateinit var mSharedPreferences: SharedPreferences

    private val sharedPreferences: SharedPreferences
        get() {
            if (!this::mSharedPreferences.isInitialized) {
                mSharedPreferences = MMKV.mmkvWithID(M_MKV_M_MAP_ID, MMKV.MULTI_PROCESS_MODE)
            }
            return mSharedPreferences
        }

    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun removeByKeys(vararg key: String) {
        for (s in key) {
            removeByKey(s)
        }
    }

    fun removeByKey(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun setBoolValue(keyName: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(keyName, value)
        editor.apply()
    }

    fun setLongValue(keyName: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(keyName, value)
        editor.apply()
    }

    fun getLongValue(keyName: String, defValue: Long): Long {
        val sp = sharedPreferences
        return sp.getLong(keyName, defValue)
    }

    fun getBoolValue(keyName: String, defValue: Boolean): Boolean {
        val sp = sharedPreferences
        return sp.getBoolean(keyName, defValue)
    }

    fun setStringValue(keyName: String, value: String?) {
        value?.run {
            val editor = sharedPreferences.edit()
            editor.putString(keyName, value)
            editor.apply()
        }
    }

    fun getStringValue(keyName: String): String {
        val sp = sharedPreferences
        return sp.getString(keyName, "") ?: ""
    }

    fun setIntValue(keyName: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(keyName, value)
        editor.apply()
    }

    fun getIntValue(keyName: String, defValue: Int): Int {
        val sp = sharedPreferences
        return sp.getInt(keyName, defValue)
    }
}