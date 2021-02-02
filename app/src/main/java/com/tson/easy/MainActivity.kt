package com.tson.easy

import com.tson.easy.activity.BaseActivity
import com.tson.easy.databinding.SampleLayoutBinding
import com.tson.easy.model.SampleViewModel

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<SampleLayoutBinding, SampleViewModel>(SampleViewModel::class.java) {

    override fun initView() {
    }

}