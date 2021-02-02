package com.tson.easy.activity

import androidx.databinding.ViewDataBinding
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 1:20 PM
 *
 * @author Tson
 */
abstract class EasyBaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    BindLoadingView<T, E>(modelClass) {

    /**
     * 默认-1，不加载loadingView，如果需要添加，继承设值 layoutId，添加后，loadingView默认隐藏：loadingView.visibility = View.GONE
     */
    override fun requestLoadingViewId(): Int = -1

    override fun initLoadingViewEnd() {}

    override fun defaultHideLoadingView() {}

}