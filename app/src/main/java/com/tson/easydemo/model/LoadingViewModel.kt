package com.tson.easydemo.model

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 5:51 PM
 *
 * @author Tson
 */
class LoadingViewModel : BaseViewModel() {

    var loadingMessage = ObservableField("")

    fun retry() {
        loadStatus.retry()
    }

    fun emptyClick() {

    }

}