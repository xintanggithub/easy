package com.easy.aop.auto

interface AutoAction {

    fun proceedBefore(action: String, map: MutableMap<Any, Any>)

    fun proceedAfter(action: String, map: MutableMap<Any, Any>)

}