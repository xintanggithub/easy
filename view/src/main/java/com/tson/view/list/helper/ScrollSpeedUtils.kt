package com.tson.view.list.helper

import androidx.recyclerview.widget.RecyclerView

/**
 *  Date 2019-08-13 19:55
 *  改变recyclerView滑动速度  默认8000
 * @author tangxin
 */
class ScrollSpeedUtils {
    companion object {
        /**
         * velocity - 滑动速度，默认800
         */
        fun setMaxFlingVelocity(recyclerView: RecyclerView, velocity: Int) {
            try {
                val field = recyclerView.javaClass.getDeclaredField("mMaxFlingVelocity")
                field.isAccessible = true
                field.set(recyclerView, velocity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}