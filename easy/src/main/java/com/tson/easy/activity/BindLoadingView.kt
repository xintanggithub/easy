package com.tson.easy.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.ViewDataBinding
import com.tson.easy.helper.ViewHelper.looperQueryLoadingView
import com.tson.easy.model.BaseViewModel
import com.tson.easy.view.LoadInterface

/**
 *  Date 2021/2/2 1:06 PM
 *
 * @author Tson
 */
abstract class BindLoadingView<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) : BaseBindingLayoutView<T, E>(modelClass), LoadInterface {

    protected lateinit var loadingView: View

    override fun bindModelType(): Int {
        return Limit.LAYOUT_ID
    }

    override fun viewModelType(): Int {
        return Limit.PUBLIC
    }

    abstract fun defaultHideLoadingView()

    abstract fun initView()

    abstract fun initLoadingViewEnd()

    override fun bindingEnd() {
        bindLoadingView()
        initView()
    }

    private fun bindLoadingView() {
        val loadingLayoutId = requestLoadingViewId()
        if (loadingLayoutId == -1) {
            return
        }
        val decorView: View = window.decorView
        val contentView = decorView.findViewById<View>(android.R.id.content)
        if (contentView is ViewGroup) {
            if (contentView.childCount <= 0) {
                return
            }
            val baseChildrenView = contentView.getChildAt(0)
            val loadingViewRoot = looperQueryLoadingView(baseChildrenView)
            loadingView = LayoutInflater.from(this).inflate(requestLoadingViewId(), null)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            if (null == loadingViewRoot) {
                contentView.also {
                    it.removeAllViews()
                    it.addView(RelativeLayout(this).also { nr ->
                        nr.layoutParams = lp
                        nr.addView(baseChildrenView)
                        nr.addView(loadingView, lp)
                    })
                }
            } else {
                loadingViewRoot.removeAllViews()
                loadingViewRoot.addView(loadingView, lp)
            }
            initLoadingViewEnd()
            defaultHideLoadingView()
        } else {
            throw Exception("activity contentView != ViewGroup")
        }
    }

}