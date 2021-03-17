package com.tson.toast

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

/**
 * Date 2021/1/28 2:03 PM
 *
 * @author Tson
 */
object ToastCustom {

    @SuppressLint("InflateParams")
    @JvmOverloads
    fun showToast(
        context: Context,
        content: String?,
        duration: Int = Toast.LENGTH_LONG,
        gravity: Int = Gravity.CENTER
    ) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_toast, null)
        val baseRoot = view.findViewById<LinearLayout>(R.id.baseToast)
        baseRoot.gravity = gravity
        val tvContent = view.findViewById<TextView>(R.id.content)
        tvContent.text = content ?: " "
        val mToast = Toast(context)
        mToast.view = view
        mToast.duration = duration
        mToast.setGravity(Gravity.CENTER, 0, 0)
        mToast.show()
    }
}