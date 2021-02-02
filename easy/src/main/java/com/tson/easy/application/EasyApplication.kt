package com.tson.easy.application

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 *  Date 2021/1/29 2:08 PM
 *
 * @author Tson
 */
open class EasyApplication : Application() {

    companion object {

        lateinit var application: Application

        var viewModelStore = ViewModelStore()

        fun of(): ViewModelProvider {
            val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            return ViewModelProvider(viewModelStore, factory)
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}
