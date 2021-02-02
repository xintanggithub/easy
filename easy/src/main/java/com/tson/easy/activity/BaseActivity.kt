package com.tson.easy.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.tson.easy.BR
import com.tson.easy.application.EasyApplication
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/1/29 3:08 PM
 *
 * @author Tson
 */
abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel>(private var modelClass: Class<E>) :
    BeforeActivity() {

    abstract val layoutId: Int

    protected lateinit var mBinding: T

    protected lateinit var viewModel: E

    override fun initBefore() {}

    abstract fun initView()

    override fun bindModel() {
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = EasyApplication.of().get(modelClass)
        viewModel.initModel()
        mBinding.setVariable(BR.vm, viewModel)
        initView()
    }

}