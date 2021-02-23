package com.tson.view.list

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator


/**
 *  Date 2020-11-11 19:58
 *
 * @author Tson
 */

object RecyclerViewHelper {
    /**
     * 打开默认局部刷新动画
     */
    fun openDefaultAnimator(rv: RecyclerView) {
        rv.itemAnimator?.run {
            addDuration = 120
            changeDuration = 250
            moveDuration = 250
            removeDuration = 120
            (this as SimpleItemAnimator).supportsChangeAnimations = true
        }
    }

    /**
     * 关闭默认局部刷新动画
     */
    fun closeDefaultAnimator(rv: RecyclerView) {
        rv.itemAnimator?.run {
            addDuration = 0
            changeDuration = 0
            moveDuration = 0
            removeDuration = 0
            (this as SimpleItemAnimator).supportsChangeAnimations = false
        }
    }

}