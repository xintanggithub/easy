package com.easy.aop.helper

import com.easy.aop.callback.DoBlock
import com.easy.aop.enumerate.Statistics.*
import com.easy.aop.utils.*

object ThreadHelper {

    fun runWithType(type: Int, doBlock: DoBlock) {
        when (type) {
            IO -> ktxRunOnIO { doBlock.doBlock() }
            UI -> ktxRunOnUi { doBlock.doBlock() }
            MAIN -> ktxRunOnMain { doBlock.doBlock() }
            SINGLE -> ktxRunOnBgSingle { doBlock.doBlock() }
            FIXED -> ktxRunOnBgFix { doBlock.doBlock() }
            CACHE -> ktxRunOnBgCache { doBlock.doBlock() }
        }
    }

    fun runWithLock(doBlock: DoBlock) {
        withLock { doBlock.doBlock() }
    }

}
