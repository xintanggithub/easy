package com.tson.easydemo

import com.easy.aop.AopManager
import com.tson.easy.application.EasyApplication

/**
 *  Date 2021/1/29 3:24 PM
 *
 * @author Tson
 */
class MyApplication : EasyApplication() {
    override fun onCreate() {
        super.onCreate()
        AopManager.instance.register(this)
    }
}