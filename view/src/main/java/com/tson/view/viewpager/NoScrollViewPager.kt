package com.tson.view.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


/**
 *  Date 2020/12/9 1:15 PM
 *
 * @author Tson
 */
class NoScrollViewPager : ViewPager {
    private var noScroll = false
    private var isRemoveTab = false

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    constructor(context: Context?) : super(context!!)

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    fun setRemoveTab(isRemoveTab: Boolean) {
        this.isRemoveTab = isRemoveTab
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(arg0)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, smoothScroll)
    }

    override fun setCurrentItem(item: Int) {
        if (isRemoveTab) {
            if (item == 4)
                super.setCurrentItem(item + 1)
            else
                super.setCurrentItem(item)
        } else {
            super.setCurrentItem(item)
        }
    }
}