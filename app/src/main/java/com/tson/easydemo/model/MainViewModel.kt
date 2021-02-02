package com.tson.easydemo.model

import android.os.Handler
import androidx.databinding.ObservableField
import com.tson.easy.model.BaseViewModel

/**
 *  Date 2021/2/2 4:58 PM
 *
 * @author Tson
 */
class MainViewModel : BaseViewModel() {

    var count = 1
    var loading = false

    var status = ObservableField(false)

    fun startLoading() {
        if (loading) {
            return
        }
        count++
        loading = true
        loadStatus.showLoading()
        Handler().postDelayed({
            status.set(count % 2 == 0)
            if (true == status.get()) {
                loadStatus.error(Exception("加载失败了"))
            } else {
                loadStatus.hideLoading()
            }
            loading = false
        }, 3000)
    }

}