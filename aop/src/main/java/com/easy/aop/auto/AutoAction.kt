package com.easy.aop.auto

interface AutoAction {

    fun proceedBefore(action: String, map: MutableMap<String, String>)

    fun proceedAfter(action: String, map: MutableMap<String, String>)

}