package com.tson.easydemo

import com.tson.easydemo.custom.BaseActivity
import com.tson.easydemo.databinding.ActivityMainBinding
import com.tson.easydemo.model.MainViewModel

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    override fun initView() {

    }

}