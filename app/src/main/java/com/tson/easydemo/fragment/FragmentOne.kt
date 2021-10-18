package com.tson.easydemo.fragment

import com.tson.easydemo.custom.BaseFragment
import com.tson.easydemo.databinding.TestFragmentBinding
import com.tson.easydemo.model.TestViewModel

class FragmentOne :
    BaseFragment<TestFragmentBinding, TestViewModel>(TestViewModel::class.java) {

    override fun initView() {
        this.error(Throwable("哈哈哈"))
    }
}