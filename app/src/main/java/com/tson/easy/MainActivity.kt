package com.tson.easy

import android.content.Intent
import android.widget.Toast
import com.tson.easy.databinding.ActivityMainBinding
import com.tson.easy.sample.MyBaseActivity
import com.tson.easy.sample.model.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity(override val layoutId: Int = R.layout.activity_main) :
    MyBaseActivity<ActivityMainBinding, MainViewModel>(MainViewModel::class.java) {

    override fun retry() {
        super.retry()
        Toast.makeText(this, "重试", Toast.LENGTH_SHORT).show()
    }

    override fun initView() {
        openTwo.setOnClickListener {
            startActivity(Intent(this, Main2Activity::class.java))
        }
    }

}