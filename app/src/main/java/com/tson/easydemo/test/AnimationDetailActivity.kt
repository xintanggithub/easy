package com.tson.easydemo.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.ArcMotion
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.tson.easydemo.R
import kotlinx.android.synthetic.main.activity_animation_detail.*

class AnimationDetailActivity : AppCompatActivity() {

    companion object {

        const val ANIMATION_IMG_1 = "animationImg"
        const val ANIMATION_IMG_2 = "animationImg2"

        fun start(activity: Activity, img: ImageView, sharedElementName: String) {
            val intent = Intent(activity, AnimationDetailActivity::class.java)
            intent.putExtra("type", sharedElementName == ANIMATION_IMG_1)
            val options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, img, sharedElementName)
            ActivityCompat.startActivity(activity, intent, options.toBundle())
        }
    }

    fun setMotion(view: View) {
        val arcMotion = ArcMotion()
        arcMotion.minimumHorizontalAngle = 50f
        arcMotion.minimumVerticalAngle = 50f
        val interpolator = AnimationUtils
            .loadInterpolator(this, android.R.interpolator.fast_out_slow_in)
        val changeBounds = CustomChangeBounds()
        changeBounds.pathMotion = arcMotion
        changeBounds.interpolator = interpolator
        changeBounds.addTarget(view)
        window.sharedElementEnterTransition = changeBounds
        window.sharedElementReturnTransition = changeBounds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_detail)
        if (intent.getBooleanExtra("type", false)) {
            setMotion(iv_one_photo)
        } else {
            setMotion(iv_one_photo2)
        }
    }
}