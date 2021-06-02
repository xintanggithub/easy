package com.tson.easydemo

import android.content.Intent
import com.tson.easydemo.custom.BaseActivity
import com.tson.easydemo.databinding.ActivityMainBinding
import com.tson.easydemo.model.MainViewModel
import com.tson.easydemo.pull.PullActivity
import com.tson.easydemo.test.AnimationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    BaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    override fun initView() {
        topull.setOnClickListener {
            startActivity(Intent(this, PullActivity::class.java))
        }
        changeAnimation.setOnClickListener {
            startActivity(Intent(this, AnimationActivity::class.java))
        }
    }

}