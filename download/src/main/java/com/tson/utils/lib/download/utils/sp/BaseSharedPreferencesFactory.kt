package com.tson.utils.lib.download.utils.sp

import android.content.Context
import android.content.SharedPreferences


/**
 * Created tangxin
 * Time 2018/10/11 下午1:45
 */
abstract class BaseSharedPreferencesFactory @JvmOverloads constructor(
    context: Context,
    private val mMode: Int = Context.MODE_PRIVATE
) {

    private val mContext: Context
    private var mSharedPreferences: SharedPreferences? = null

    protected abstract val key: String

    val sharedPreferences: SharedPreferences
        get() {
            if (mSharedPreferences == null) {
                mSharedPreferences = mContext.getSharedPreferences(key, mMode)
            }
            return mSharedPreferences!!
        }

    init {
        mContext = context.applicationContext
    }

    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
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

    fun getBoolValue(keyName: String, defValue: Boolean): Boolean {
        val sp = sharedPreferences
        return sp.getBoolean(keyName, defValue)
    }

    fun setStringValue(keyName: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(keyName, value)
        editor.apply()
    }

    fun getStringValue(keyName: String): String? {
        val sp = sharedPreferences
        return sp.getString(keyName, "")
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
