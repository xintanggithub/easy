package com.tson.easy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.tson.easy.BR
import com.tson.easy.application.EasyApplication
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 3:00 PM
 *
 * @author Tson
 */

abstract class BeforeFragment<T : ViewDataBinding, E : BaseViewModel>(var modelClass: Class<E>?) :
    Fragment() {

    protected lateinit var mBinding: T
    protected lateinit var viewModel: E

    abstract val layoutId: Int

    abstract fun initBefore()

    abstract fun viewCreated()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = inflater.inflate(layoutId, container, false)
        if (null != modelClass) {
            viewModel = getViewModel(modelClass!!)
            mBinding = DataBindingUtil.bind(fragmentView)!!
            mBinding.setVariable(BR.vm, viewModel)
            viewModel.initModel()
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBefore()
        viewCreated()
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return EasyApplication.of().get(modelClass)
    }

}