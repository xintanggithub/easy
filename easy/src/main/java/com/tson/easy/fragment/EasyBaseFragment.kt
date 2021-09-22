package com.tson.easy.fragment

import androidx.databinding.ViewDataBinding
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 3:41 PM
 *
 * @author Tson
 */
abstract class EasyBaseFragment<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>?) :
    BindLoadingView<T, E>(modelClass) {

    override fun requestLoadingViewId(): Int = -1

    override fun defaultHideLoadingView() {}

    override fun initLoadingViewEnd() {}

    override fun anyHandle(status: Int, any: Any) {}

    override fun showLoading(message: String) {}
    override fun success() {}
    override fun success(any: Any) {}

}