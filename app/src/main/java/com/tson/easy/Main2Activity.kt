package com.tson.easy

import com.tson.easy.databinding.TwoLayoutBinding
import com.tson.easy.sample.MyBaseActivity
import com.tson.easy.sample.model.Main2ViewModel

/**
 *  Date 2021/2/2 2:46 PM
 *
 * @author Tson
 */
class Main2Activity(override val layoutId: Int = R.layout.two_layout) :
    MyBaseActivity<TwoLayoutBinding, Main2ViewModel>(Main2ViewModel::class.java) {

    override fun initView() {

    }

}