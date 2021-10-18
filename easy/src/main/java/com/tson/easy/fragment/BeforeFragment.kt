package com.tson.easy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tson.easy.BR
import com.tson.easy.activity.Limit
import com.tson.easy.application.EasyApplication
import com.tson.easy.model.BaseViewModel
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

/**
 *  Date 2021/2/2 3:00 PM
 *
 * @author Tson
 */

abstract class BeforeFragment<T : ViewDataBinding, E : BaseViewModel>(var modelClass: Class<E>?) : Fragment() {

    protected lateinit var mBinding: T

    protected lateinit var viewModel: E

    abstract val layoutId: Int

    @Limit.BindModelType
    abstract fun bindModelType(): Int

    @Limit.ViewModelType
    abstract fun viewModelType(): Int

    abstract fun initBefore()

    abstract fun viewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return when (bindModelType()) {
            Limit.LAYOUT_ID -> inflaterBinder(inflater, container)
            else -> methodBinder()
        }
    }

    private fun methodBinder(): View? {
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

            mBinding.setVariable(BR.vm, viewModel)
            viewModel.initModel()
            return mBinding.root
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun inflaterBinder(inflater: LayoutInflater, container: ViewGroup?): View? {
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
                ViewModelProvider.AndroidViewModelFactory(EasyApplication.application)
            ).get(modelClass)
        }
    }

}