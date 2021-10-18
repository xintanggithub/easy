package com.tson.easy.activity

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tson.easy.BR
import com.tson.easy.application.EasyApplication
import com.tson.easy.model.BaseViewModel
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 *  Date 2021/1/29 3:08 PM
 *
 * @author Tson
 */
abstract class BaseBindingLayoutView<T : ViewDataBinding, E : BaseViewModel>(private var modelClass: Class<E>) : BeforeActivity() {

    abstract val layoutId: Int

    @Limit.BindModelType
    abstract fun bindModelType(): Int

    @Limit.ViewModelType
    abstract fun viewModelType(): Int

    protected lateinit var mBinding: T

    protected lateinit var viewModel: E

    override fun initBefore() {}

    protected abstract fun bindingEnd()

    override fun bindModel() {
        when (bindModelType()) {
            Limit.LAYOUT_ID -> layoutBindModel()
            Limit.METHOD -> methodBindModel()
        }
    }

    private fun methodBindModel() {
        try {
            val actualTypeArguments =
                (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            @Suppress("UNCHECKED_CAST") val viewModelClass: Class<E> =
                actualTypeArguments[1] as Class<E>
            viewModel = getViewModel(viewModelClass)
            @Suppress("UNCHECKED_CAST") val dataBindingClass: Class<T> =
                actualTypeArguments[0] as Class<T>
            val method: Method = dataBindingClass.getMethod("inflate", LayoutInflater::class.java)
            @Suppress("UNCHECKED_CAST")
            mBinding = method.invoke(null, layoutInflater) as T
            setContentView(mBinding.root)
            viewModel.initModel()
            mBinding.setVariable(BR.vm, viewModel)
            bindingEnd()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun layoutBindModel() {
        mBinding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = getViewModel(modelClass)
        viewModel.initModel()
        mBinding.setVariable(BR.vm, viewModel)
        bindingEnd()
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>): T {
        return getViewModel(
            modelClass, when (viewModelType()) {
                Limit.PROJECT -> false
                else -> true
            }
        )
    }

    protected fun <T : ViewModel> getViewModel(modelClass: Class<T>, componentUse: Boolean): T {
        return when (componentUse) {
            true -> EasyApplication.of().get(modelClass)
            else -> ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
            ).get(modelClass)
        }
    }
}