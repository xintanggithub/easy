package com.tson.easy

import com.tson.easy.databinding.TwoLayoutBinding
import com.tson.easy.sample.MyBaseActivity
import com.tson.easy.sample.model.MainViewModel

/**
 *  Date 2021/2/2 2:46 PM
 *
 * @author Tson
 */
class Main2Activity(override val layoutId: Int = R.layout.two_layout) :
    MyBaseActivity<TwoLayoutBinding, MainViewModel>(MainViewModel::class.java) {

    override fun initView() {

    }

}