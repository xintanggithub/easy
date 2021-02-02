package com.tson.easy.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity

/**
 *  Date 2021/1/29 3:09 PM
 *
 * @author Tson
 */
abstract class BeforeActivity : FragmentActivity() {

    companion object {
        const val LOADING_VIEW_TAG = "loadingView"
    }

    abstract fun initBefore()

    abstract fun bindModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBefore()
        bindModel()
    }

}