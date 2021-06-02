package com.tson.easydemo.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tson.easydemo.R
import com.tson.easydemo.test.AnimationDetailActivity.Companion.ANIMATION_IMG_1
import com.tson.easydemo.test.AnimationDetailActivity.Companion.ANIMATION_IMG_2
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        openDetail.setOnClickListener {
            AnimationDetailActivity.start(this, iv_one_photo, ANIMATION_IMG_1)
        }
        openDetail2.setOnClickListener {
            AnimationDetailActivity.start(this, iv_one_photo2, ANIMATION_IMG_2)
        }
    }
}