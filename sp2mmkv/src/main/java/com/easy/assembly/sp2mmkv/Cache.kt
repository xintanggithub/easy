package com.easy.assembly.sp2mmkv

import android.app.Application
import com.tencent.mmkv.MMKV

class Cache {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Cache() }
    }

    private val sp = BaseSharedPreferencesFactory()

    fun init(application: Application) {
        MMKV.initialize(application)
    }

    fun cacheBase() = sp

}