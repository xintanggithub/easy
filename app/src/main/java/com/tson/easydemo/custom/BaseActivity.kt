package com.tson.easydemo.custom

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tson.easy.activity.EasyBaseActivity
import com.tson.easy.model.BaseViewModel
import com.tson.easydemo.databinding.LoadingLayoutBinding
import com.tson.easydemo.model.LoadingViewModel

/**
 *  Date 2021/2/2 4:59 PM
 *
 * @author Tson
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {

    lateinit var loadingBinding: LoadingLayoutBinding
    lateinit var loadingViewModel: LoadingViewModel

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
        hideRoot()
    }

    private fun showRoot() {
        loadingBinding.root.visibility = View.VISIBLE
    }

    private fun hideRoot() {
        loadingBinding.root.visibility = View.GONE
    }

    override fun initLoadingViewEnd() {
        loadingBinding = DataBindingUtil.bind(loadingView)!!
        loadingViewModel = getViewModel(LoadingViewModel::class.java)
        loadingBinding.loading = loadingViewModel
        viewModel.loadStatus = this
    }

    override fun error(error: Throwable) {
        showRoot()
        loadingViewModel.loadingMessage.set("❌错误信息：${error.message}")
    }

    override fun hideLoading() {
        hideRoot()
    }

    override fun retry() {
        hideRoot()

    }

    override fun showLoading() {
        showRoot()
        loadingViewModel.loadingMessage.set("现在正在加载中...")
    }

}