package com.tson.easy.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.ViewDataBinding
import com.tson.easy.activity.Limit
import com.tson.easy.helper.ViewHelper
import com.tson.easy.model.BaseViewModel
import com.tson.easy.view.LoadInterface

/**
 *  Date 2021/2/2 3:17 PM
 *
 * @author Tson
 */
abstract class BindLoadingView<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>?) :
    BeforeFragment<T, E>(modelClass), LoadInterface {

    protected lateinit var loadingView: View

    override fun bindModelType(): Int {
        return Limit.LAYOUT_ID
    }

    override fun viewModelType(): Int {
        return Limit.PUBLIC
    }

    override fun initBefore() {}

    abstract fun defaultHideLoadingView()

    abstract fun initLoadingViewEnd()

    abstract fun initView()

    override fun viewCreated() {
        bindLoadingView()
        initView()
    }

    private fun bindLoadingView() {
        val loadingLayoutId = requestLoadingViewId()
        if (loadingLayoutId == -1) {
            return
        }
        val contentView = mBinding.root
        if (contentView is ViewGroup) {
            if (contentView.childCount <= 0) {
                return
            }
            val baseChildrenView = contentView.getChildAt(0)
            val loadingViewRoot = ViewHelper.looperQueryLoadingView(contentView)
            loadingView = LayoutInflater.from(context).inflate(requestLoadingViewId(), null)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            if (null == loadingViewRoot) {
                contentView.also {
                    it.removeAllViews()
                    it.addView(RelativeLayout(context).also { nr ->
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
            throw Exception("fragment contentView != ViewGroup")
        }
    }

}