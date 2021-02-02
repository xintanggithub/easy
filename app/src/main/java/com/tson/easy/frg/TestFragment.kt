package com.tson.easy.frg

import com.tson.easy.R
import com.tson.easy.databinding.FrgLayoutBinding
import com.tson.easy.frg.model.FrgModel
import com.tson.easy.sample.MyBaseFragment

/**
 *  Date 2021/2/2 3:27 PM
 *
 * @author Tson
 */
class TestFragment(override val layoutId: Int = R.layout.frg_layout) :
    MyBaseFragment<FrgLayoutBinding, FrgModel>(FrgModel::class.java) {

    override fun initView() {

    }

}