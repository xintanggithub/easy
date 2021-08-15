package com.tson.utils.input

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


/**
 *  Date 2020-10-15 10:31
 *
 * @author Tson
 */
class InputUtils {

    companion object {
        private const val TAG = "DeleteTips"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { InputUtils() }
    }

    fun showInput(context: Context, et: EditText) {
        et.requestFocus()
        Handler().postDelayed({
            et.setSelection(et.text.toString().length)
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
        }, 100)
    }

    fun hide(context: Context, edit: EditText) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(edit.windowToken, 0)
    }

    fun hideInput(context: Activity) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val v = context.window.peekDecorView()
        if (null != v) {
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }

}
