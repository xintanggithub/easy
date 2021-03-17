package com.tson.utils.display

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.DisplayMetrics
import android.view.View

/**
 *  Created tangxin
 *  Time 2019/5/8 3:24 PM
 */
object DisplayUtils {
    /**
     * 将px值转换为dp值
     *
     * @param pxValue the px value
     * @return the int
     */
    fun px2dp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dp值转换为px值
     *
     * @param dpValue the dp value
     * @return the int
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值
     *
     * @param pxValue the px value
     * @return the int
     */
    fun px2sp(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值
     *
     * @param dpValue the dp value
     * @return the int
     */
    fun sp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.scaledDensity
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     *
     * @param context the context
     * @return the screen width pixels
     */
    fun getScreenWidthPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    /**
     * 获取屏幕高度
     *
     * @param context the context
     * @return the screen height pixels
     */
    fun getScreenHeightPixels(context: Activity): Int {
        val metric = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    /**
     * 将一个view转换成bitmap位图
     *
     * @param view 要转换的View
     * @return view转换的bitmap bitmap
     */
    fun viewToBitmap(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        view.draw(Canvas(bitmap))
        return bitmap
    }

    /**
     * 获取模糊虚化的bitmap
     *
     * @param context the context
     * @param bitmap  要模糊的图片
     * @param radius  模糊等级 >=0 && <=25
     * @return blur bitmap
     */
    fun getBlurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurBitmap(context, bitmap, radius)
        } else bitmap
    }

    /**
     * android系统的模糊方法
     *
     * @param context the context
     * @param bitmap  要模糊的图片
     * @param radius  模糊等级 >=0 && <=25
     * @return the bitmap
     */
    fun blurBitmap(context: Context, bitmap: Bitmap, radius: Int): Bitmap {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Let's create an empty bitmap with the same size of the bitmap we want to blur
            val outBitmap = Bitmap.createBitmap(
                bitmap.width, bitmap.height, Bitmap
                    .Config.ARGB_8888
            )
            //Instantiate a new Renderscript
            val rs = RenderScript.create(context)
            //Create an Intrinsic Blur Script using the Renderscript
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
            val allIn = Allocation.createFromBitmap(rs, bitmap)
            val allOut = Allocation.createFromBitmap(rs, outBitmap)
            //Set the radius of the blur
            blurScript.setRadius(radius.toFloat())
            //Perform the Renderscript
            blurScript.setInput(allIn)
            blurScript.forEach(allOut)
            //Copy the final bitmap created by the out Allocation to the outBitmap
            allOut.copyTo(outBitmap)
            //recycle the original bitmap
            bitmap.recycle()
            //After finishing everything, we destroy the Renderscript.
            rs.destroy()
            return outBitmap
        } else {
            return bitmap
        }
    }
}