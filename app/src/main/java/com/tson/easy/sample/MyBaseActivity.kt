package com.tson.easy.sample

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tson.easy.R
import com.tson.easy.activity.EasyBaseActivity
import com.tson.easy.databinding.CLoadingBinding
import com.tson.easy.model.BaseViewModel
import com.tson.easy.sample.model.LoadingModel

/**
 *  Date 2021/2/2 1:46 PM
 *
 * @author Tson
 */
abstract class MyBaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {

    private lateinit var loadingBinding: CLoadingBinding

    //布局设置
    override fun requestLoadingViewId(): Int = R.layout.c_loading

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
        loadingBinding.root.visibility = View.GONE
    }

    //loadingView初始化完成
    override fun initLoadingViewEnd() {
        loadingBinding = DataBindingUtil.bind(loadingView)!!
        loadingBinding.loading = getViewModel(LoadingModel::class.java)
        // 绑定loadView回调
        viewModel.loadStatus = this
        loadingBinding.retry.setOnClickListener {
            retry()
        }
    }

    override fun showLoading() {
        loadingBinding.root.visibility = View.VISIBLE
        loadingBinding.loadingText.run {
            visibility = View.VISIBLE
            text = "showLoading"
        }
    }

    override fun hideLoading() {
        loadingBinding.root.visibility = View.GONE
        loadingBinding.loadingText.visibility = View.GONE
    }

    override fun retry() {

    }

    override fun error(error: Throwable) {
        loadingBinding.root.visibility = View.VISIBLE
        loadingBinding.loadingText.run {
            visibility = View.VISIBLE
            text = " ${error.message}"
        }
    }

}