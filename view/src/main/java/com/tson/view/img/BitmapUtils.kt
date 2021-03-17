package com.tson.view.img

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable


/**
 *  Date 2019-07-02 16:23
 *
 * @author tangxin
 */
class BitmapUtils {

    companion object {

        //drawable转bitmap
        fun drawableToBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else
                    Bitmap.Config.RGB_565
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            drawable.draw(canvas)
            return bitmap
        }

        //资源图片转bitmap
        fun decodeSampledBitmapFromResource(
            res: Resources,
            resId: Int,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap {
            val options = BitmapFactory.Options()
            // 先将inJustDecodeBounds设置为true不会申请内存去创建Bitmap，返回的是一个空的Bitmap，但是可以获取
            //图片的一些属性，例如图片宽高，图片类型等等。
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, options)

            // 计算inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // 加载压缩版图片
            options.inJustDecodeBounds = false
            // 根据具体情况选择具体的解码方法
            return BitmapFactory.decodeResource(res, resId, options)
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            // 原图片的宽高
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val halfHeight = height / 2
                val halfWidth = width / 2
                // 计算inSampleSize值
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }
            return inSampleSize
        }

    }

}