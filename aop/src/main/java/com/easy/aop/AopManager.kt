package com.easy.aop

import android.app.Application
import com.easy.aop.auto.AutoAction
import com.easy.aop.callback.DoProceed
import com.easy.aop.helper.AutoHelper
import com.easy.aop.utils.log.AopLogHelper
import com.easy.aop.utils.log.ListenerLog

class AopManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AopManager() }
    }

    private lateinit var application: Application

    fun register(application: Application): AopManager {
        this.application = application
        return instance
    }

    fun getApplication(): Application {
        if (!this::application.isInitialized) {
            Throwable("this::application.isInitialized == false")
        }
        return application
    }

    fun beforeCallback(action: String, map: MutableMap<String, String>, proceed: DoProceed) {
        AutoHelper.instance.beforeCallback(action, map, proceed)
    }

    fun afterCallback(action: String, map: MutableMap<String, String>) {
        AutoHelper.instance.afterCallback(action, map)
    }

    fun setAutoListener(autoAction: AutoAction) {
//        AutoHelper.instance.addAutoListener(autoAction)
        AutoHelper.instance.setAutoListener(autoAction)
    }

    fun addLogListener(listenerLog: ListenerLog) {
        AopLogHelper.instance.addListenerLog(listenerLog)
    }

//    fun removeAutoListener(hashCode: Int) {
//        AutoHelper.instance.removeAutoListener(hashCode)
//    }

    fun removeLogListener(hashCode: Int) {
        AopLogHelper.instance.removeListenerLog(hashCode)
    }

//    fun removeAutoListener(action: AutoAction) {
//        AutoHelper.instance.removeAutoListener(action)
//    }

    fun removeLogListener(listenerLog: ListenerLog) {
        AopLogHelper.instance.removeListenerLog(listenerLog)
    }

}