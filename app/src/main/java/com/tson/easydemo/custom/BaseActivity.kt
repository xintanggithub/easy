package com.tson.easydemo.custom

import androidx.databinding.ViewDataBinding
import com.tson.easy.activity.EasyBaseActivity
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 4:59 PM
 *
 * @author Tson
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
    }

    override fun initLoadingViewEnd() {
        super.initLoadingViewEnd()
    }

    override fun error(error: Throwable) {
    }

    override fun hideLoading() {
    }

    override fun retry() {
    }

    override fun showLoading() {
    }

}