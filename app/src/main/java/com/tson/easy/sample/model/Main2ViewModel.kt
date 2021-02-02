package com.tson.easy.sample.model

import androidx.databinding.ObservableField
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 2:04 PM
 *
 * @author Tson
 */
class Main2ViewModel : BaseViewModel() {

    val sampleContent = ObservableField("这是没有设置loadingView父布局的")

    fun showLoading() {
        loadStatus.showLoading()
    }

    fun hideLoading() {
        loadStatus.hideLoading()
    }

    fun error() {
        loadStatus.error(Throwable("抛个异常过去，看看errorView"))
    }

}