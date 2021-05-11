package com.tson.view.dialog

import android.app.Activity
import android.os.Handler
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.tson.view.R


/**
 * Date 2020-10-29 19:58
 *
 * @author Tson
 */
class SubmitLoading {
    companion object {
        const val TAG = "SubmitLoading"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { SubmitLoading() }
    }

    lateinit var dialog: MyDialog
    lateinit var loadingTv: TextView
    lateinit var pb: ProgressBar
    lateinit var siv: ImageView


    fun show(context: Activity) {
        show(context, false)
    }

    fun show(context: Activity, msg: String) {
        show(context, msg, false)
    }

    fun show(context: Activity, cancelable: Boolean) {
        show(context, "loading", cancelable)
    }

    fun show(context: Activity, msg: String, cancelable: Boolean) {
        val view = context.layoutInflater.inflate(R.layout.submit_loading, null)
        loadingTv = view.findViewById(R.id.loading)
        pb = view.findViewById(R.id.pb)
        siv = view.findViewById(R.id.siv)
        loadingTv.text = msg
        dialog = MyDialog(context, view, R.style.DialogThemes).also {
            it.setCancelable(cancelable)
            it.show()
        }
    }

    fun success(end: () -> Unit = {}) {
        success("successful!", end)
    }

    fun success(msg: String, end: () -> Unit = {}) {
        hideTips(msg, 400, true, end)
    }

    fun success(msg: String, time: Long, end: () -> Unit = {}) {
        hideTips(msg, time, true, end)
    }

    fun error(end: () -> Unit = {}) {
        error("error!", end)
    }

    fun error(msg: String, end: () -> Unit = {}) {
        error(msg, 400, end)
    }

    fun error(msg: String, time: Long, end: () -> Unit = {}) {
        hideTips(msg, time, false, end)
    }

    fun hideTips(msg: String, showTime: Long, status: Boolean, end: () -> Unit = {}) {
        if (this::pb.isInitialized && this::siv.isInitialized) {
            changeText("")
            animationHide(pb)
            animationShow(siv, status)
            Handler().postDelayed({
                changeText(msg)
            }, 250)
            Handler().postDelayed({
                end()
                hide()
            }, showTime + 200)
        } else {
            hide()
        }
    }

    fun animationHide(view: View) {
        val animation = AlphaAnimation(1f, 0f)
        animation.duration = 150
        animation.isFillEnabled = true
        animation.fillBefore = false
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                view.visibility = View.GONE
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        view.startAnimation(animation)
    }

    fun animationShow(view: ImageView, status: Boolean) {
        if (status) {
            view.setImageResource(R.drawable.ic_done_black_24dp)
        } else {
            view.setImageResource(R.drawable.ic_clear_black_24dp)
        }
        val animation = AlphaAnimation(0f, 1f)
        animation.duration = 200
        animation.isFillEnabled = true
        animation.fillBefore = false
        animation.fillAfter = true
        view.startAnimation(animation)
        view.visibility = View.VISIBLE
    }

    fun changeText(str: String) {
        if (this::loadingTv.isInitialized) {
            loadingTv.text = str
        }
    }

    fun hide() {
        if (this::dialog.isInitialized) {
            dialog.dismiss()
        }
    }

}
