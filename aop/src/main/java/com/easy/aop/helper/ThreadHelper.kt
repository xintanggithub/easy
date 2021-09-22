package com.easy.aop.helper

import com.easy.aop.enumerate.Statistics.*

object ThreadHelper {

    fun runWithType(type: Int, doBlock: DoBlock) {
        when (type) {
            IO -> {
            }
            UI -> {
            }
            MAIN -> {
            }
            SINGLE -> {
            }
            FIXED -> {
            }
            CACHE -> {
            }
        }
    }

}

interface DoBlock {
    fun doBlock()
}