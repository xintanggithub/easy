package com.tson.easy.activity

import androidx.databinding.ViewDataBinding
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 1:20 PM
 *
 * @author Tson
 */
abstract class EasyBaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) : BindLoadingView<T, E>(modelClass) {

    override fun requestLoadingViewId(): Int = -1

    override fun initLoadingViewEnd() {}

    override fun defaultHideLoadingView() {}

    override fun showLoading(message: String) {
    }

    override fun anyHandle(status: Int, any: Any) {
    }

    override fun success() {
    }

    override fun success(any: Any) {
    }

}