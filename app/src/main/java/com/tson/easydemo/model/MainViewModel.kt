package com.tson.easydemo.model

import android.os.Handler
import androidx.databinding.ObservableField
import com.tson.easy.model.BaseViewModel
import com.tson.easydemo.api.LoginRequest
import com.tson.easydemo.api.TestApi
import com.tson.http.Request
import com.tson.http.config.HttpConfig
import com.tson.http.factory.RetrofitFactory

/**
 *  Date 2021/2/2 4:58 PM
 *
 * @author Tson
 */
class MainViewModel : BaseViewModel() {

    lateinit var api: TestApi

    override fun initModel() {
        super.initModel()

        HttpConfig.instance.host("http://channel.xtcodev.cn/")
        if (!this::api.isInitialized) {
            api = RetrofitFactory.defaultCreateApi(TestApi::class.java)
        }
    }

    private fun getData() {
        Request.call({ api.login(LoginRequest()) }, {
            loadStatus.showLoading()
        }, {
            loadStatus.error(Throwable("哈哈哈哈"))
//            loadStatus.hideLoading()
        }, {
            loadStatus.error(it)
        })
    }

    fun userLoadingViewMethod() {
        loadStatus.showLoading() // 显示
        loadStatus.hideLoading() // 隐藏
        loadStatus.error(Exception("error message")) //错误
        loadStatus.retry() //重试
    }

    var status = ObservableField(false)

    fun startLoading() {
        getData()
    }

}