package com.tson.easydemo.fragment

import com.tson.easydemo.R
import com.tson.easydemo.custom.BaseFragment
import com.tson.easydemo.databinding.TestFragmentBinding
import com.tson.easydemo.model.TestViewModel

class FragmentOne(override val layoutId: Int = R.layout.test_fragment) :
    BaseFragment<TestFragmentBinding, TestViewModel>(TestViewModel::class.java) {

    override fun initView() {
        this.error(Throwable("哈哈哈"))
    }

}