package com.tson.view.img

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop

import java.security.MessageDigest

/**
 * Date 2019/6/15 1:31 PM
 *
 * @author tangxin
 */
class GlideBlurTransformation : CenterCrop {
    private var context: Context? = null
    private var config: Bitmap.Config? = null
    private var size = 1
    //最低模糊度，25为最大值，默认模糊等级最大
    private var blurRadiusSize = 25f


    constructor(context: Context) {
        this.context = context
        this.config = Bitmap.Config.ARGB_4444
    }


    constructor(context: Context, config: Bitmap.Config, size: Int) {
        this.context = context
        this.config = config
        this.size = size
    }

    //用于想自定义最低模糊度当方法
    constructor(context: Context, config: Bitmap.Config, size: Int, blurRadiusSize: Float) {
        GlideBlurTransformation(context, config, size)
        this.blurRadiusSize = blurRadiusSize
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val bitmap = super.transform(pool, toTransform, outWidth, outHeight)
        return blurBitmap(context, bitmap, 25f, (outWidth * 0.5).toInt(), (outHeight * 0.5).toInt())
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    /**
     * @param image      需要模糊的图片
     * @param blurRadius 模糊的半径（1-25之间）
     * @return 模糊处理后的Bitmap
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun blurBitmap(context: Context?, image: Bitmap, blurRadius: Float, outWidth: Int, outHeight: Int): Bitmap {
        // 将缩小后的图片做为预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(image, outWidth / size, outHeight / size, false)
        inputBitmap.config = config
        // 创建一张渲染后的输出图片
        val outputBitmap = Bitmap.createBitmap(inputBitmap)
        // 创建RenderScript内核对象
        val rs = RenderScript.create(context)
        // 创建一个模糊效果的RenderScript的工具对象
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)
        // 设置渲染的模糊程度, 25f是最大模糊度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            blurScript.setRadius(blurRadius)
        }
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn)
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut)
        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }
}
