package com.tson.easydemo.custom

import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tson.easy.activity.EasyBaseActivity
import com.tson.easy.model.BaseViewModel
import com.tson.easydemo.R
import com.tson.easydemo.databinding.LoadingLayoutBinding
import com.tson.easydemo.model.LoadingViewModel

/**
 *  Date 2021/2/2 4:59 PM
 *
 * @author Tson
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(modelClass: Class<E>) :
    EasyBaseActivity<T, E>(modelClass) {

    override val layoutId: Int
        get() = -1

    // loadingView布局binding
    lateinit var loadingBinding: LoadingLayoutBinding

    lateinit var loadingViewModel: LoadingViewModel

    // 设定loadingView布局，也可以在子类实现，如果在子类实现，以下binding和viewModel的操作也同步在子类处理
    override fun requestLoadingViewId(): Int = R.layout.loading_layout

    override fun defaultHideLoadingView() {
        super.defaultHideLoadingView()
        // 默认的公共布局处理，一般做loadingView默认显示或隐藏，触发时机在initView方法之前
        hideRoot()
    }

    private fun showRoot() {
        loadingBinding.root.visibility = View.VISIBLE
    }

    private fun hideRoot() {
        loadingBinding.root.visibility = View.GONE
    }

    /**
     * loadingView绑定到contentView成功后触发，触发时机在 [defaultHideLoadingView] 之前
     */
    override fun initLoadingViewEnd() {
        // dataBinding绑定view
        loadingBinding = DataBindingUtil.bind(loadingView)!!
        // 获取ViewModel
        loadingViewModel = LoadingViewModel()
        // 绑定ViewModel
        loadingBinding.loading = loadingViewModel
        // 关联页面业务ViewModel和公共loadingView和errorView的核心代码，由接口实现
        viewModel.loadStatus = this
        loadingViewModel.loadStatus = this
    }

    override fun error(error: Throwable) {
        // 显示错误信息
        showRoot()
        loadingViewModel.loadingMessage.set("❌错误信息：${error.message}")
    }

    override fun hideLoading() {
        // 隐藏loading 或 error
        loadingViewModel.loadingMessage.set("")
        hideRoot()
    }

    override fun retry() {
        //  重试
        hideRoot()
        Toast.makeText(this, "handle  retry ", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        // 显示loading
        showRoot()
        loadingViewModel.loadingMessage.set("")
        loadingViewModel.loadingMessage.set("现在正在加载中...")
    }

}