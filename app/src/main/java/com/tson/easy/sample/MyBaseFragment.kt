package com.tson.easy.sample

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tson.easy.R
import com.tson.easy.databinding.C2LoadingBinding
import com.tson.easy.fragment.EasyBaseFragment
import com.tson.easy.model.BaseViewModel
import com.tson.easy.sample.model.LoadingModel

/**
 *  Date 2021/2/2 1:46 PM
 *
 * @author Tson
 */
abstract class MyBaseFragment<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>?) :
    EasyBaseFragment<T, E>(modelClass) {

    private lateinit var loadingBinding: C2LoadingBinding

    override fun requestLoadingViewId(): Int = R.layout.c2_loading

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
        loadingBinding.root.visibility = View.GONE
    }

    override fun initLoadingViewEnd() {
        loadingBinding = DataBindingUtil.bind(loadingView)!!
        loadingBinding.loading = getViewModel(LoadingModel::class.java)
        if (null != modelClass) {
            viewModel.loadStatus = this
        }
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