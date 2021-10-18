package com.tson.easydemo

import android.content.Intent
import com.tson.easy.activity.Limit
import com.tson.easydemo.custom.BaseActivity
import com.tson.easydemo.databinding.ActivityMainBinding
import com.tson.easydemo.fragment.FragmentOne
import com.tson.easydemo.fragment.MainFragmentAdapter
import com.tson.easydemo.model.MainViewModel
import com.tson.easydemo.pull.PullActivity
import com.tson.easydemo.test.AnimationActivity

class MainActivity :
    BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    /////////////// 无layoutId传入的方式

    override fun bindModelType(): Int {
        return Limit.METHOD
    }

    override fun viewModelType(): Int {
        return Limit.PROJECT
    }

    override fun initView() {
        mBinding.topull.setOnClickListener {
            startActivity(Intent(this, PullActivity::class.java))
        }
        mBinding.changeAnimation.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
        }
        mBinding.vp.adapter = MainFragmentAdapter(
            supportFragmentManager,
            mutableListOf(FragmentOne(), FragmentOne(), FragmentOne())
        )
    }

}