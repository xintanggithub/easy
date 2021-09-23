package com.easy.aop

import com.easy.aop.utils.log.AopLogHelper
import com.easy.aop.utils.log.ListenerLog

class AopManager {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AopManager() }
    }

    fun addListenerLog(listenerLog: ListenerLog) {
        AopLogHelper.instance.addListenerLog(listenerLog)
    }

    fun removeListenerLog(hashCode: Int) {
        AopLogHelper.instance.removeListenerLog(hashCode)
    }

    fun removeListenerLog(listenerLog: ListenerLog) {
        AopLogHelper.instance.removeListenerLog(listenerLog)
    }

}