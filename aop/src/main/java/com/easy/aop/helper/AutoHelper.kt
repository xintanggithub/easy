package com.easy.aop.helper

import com.easy.aop.auto.AutoAction

class AutoHelper {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { AutoHelper() }
    }

    private var autoMap: AutoAction = object : AutoAction {
        override fun proceedBefore(action: String, map: MutableMap<String, String>) {
        }

        override fun proceedAfter(action: String, map: MutableMap<String, String>) {
        }
    }

    fun beforeCallback(action: String, map: MutableMap<String, String>) {
//        for (mutableEntry in autoMap) {
//            mutableEntry.value.proceedBefore(action, map)
//        }
        autoMap.proceedBefore(action, map)
    }

    fun afterCallback(action: String, map: MutableMap<String, String>) {
//        for (mutableEntry in autoMap) {
//            mutableEntry.value.proceedAfter(action, map)
//        }
        autoMap.proceedAfter(action, map)
    }

    fun setAutoListener(action: AutoAction) {
        autoMap = action
    }

//    fun addAutoListener(autoAction: AutoAction) {
//        autoMap[autoAction.hashCode()] = autoAction
//    }
//
//    fun removeAutoListener(hashCode: Int) {
//        autoMap.remove(hashCode)
//    }
//
//    fun removeAutoListener(autoAction: AutoAction) {
//        removeAutoListener(autoAction.hashCode())
//    }

}