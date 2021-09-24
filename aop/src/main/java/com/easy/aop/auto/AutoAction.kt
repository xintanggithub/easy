package com.easy.aop.auto

import com.easy.aop.callback.DoProceed

interface AutoAction {

    fun proceedBefore(action: String, map: MutableMap<String, String>, proceed: DoProceed)

    fun proceedAfter(action: String, map: MutableMap<String, String>)

}