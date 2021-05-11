package com.tson.view.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.tson.view.R

/**
 * Date 2020-09-27 18:50
 *
 * @author Tson
 */
class MyDialog(context: Context, layout: View, style: Int) : Dialog(context, style) {

    constructor(context: Context, layout: View) : this(context, layout, R.style.DialogThemes)

    init {

        setContentView(layout)

        val window = window

        val params = window!!.attributes

        params.gravity = Gravity.CENTER

        window.attributes = params
    }
}

