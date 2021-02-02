package com.tson.easy.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.tson.easy.BR
import com.tson.easy.application.EasyApplication
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/1/29 3:08 PM
 *
 * @author Tson
 */
abstract class BaseBindingLayoutView<T : ViewDataBinding, E : BaseViewModel>(private var modelClass: Class<E>) :
    BeforeActivity() {

    abstract val layoutId: Int

    protected lateinit var mBinding: T

    protected lateinit var viewModel: E

    override fun initBefore() {}

    protected abstract fun bindingEnd()

    override fun bindModel() {
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = getViewModel(modelClass)
        viewModel.initModel()
        mBinding.setVariable(BR.vm, viewModel)
        bindingEnd()
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return EasyApplication.of().get(modelClass)
    }
}