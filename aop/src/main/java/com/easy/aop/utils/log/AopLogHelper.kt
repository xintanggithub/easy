package com.easy.aop.utils.log

class AopLogHelper {
    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AopLogHelper() }
    }

    private val listenerLogMap = mutableMapOf<Int, ListenerLog>()

    fun addListenerLog(listenerLog: ListenerLog) {
        listenerLogMap[listenerLog.hashCode()] = listenerLog
    }

    fun removeListenerLog(hashCode: Int) {
        listenerLogMap.remove(hashCode)
    }

    fun removeListenerLog(listenerLog: ListenerLog) {
        listenerLogMap.remove(listenerLog.hashCode())
    }

    fun logCallback(tag: String?, content: String?) {
        for (mutableEntry in listenerLogMap) {
            mutableEntry.value.print(tag ?: "", content ?: "")
        }
    }

}

interface ListenerLog {
    fun print(tag: String, content: String)
}