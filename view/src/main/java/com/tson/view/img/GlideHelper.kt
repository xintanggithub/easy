package com.tson.view.img

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.tson.view.R

/**
 * Date 2019/6/3 11:16 AM
 *
 * @author tangxin
 */
class GlideHelper {

    companion object {

        /**
         * 设置配置项：如占位图、错误时显示的图
         */
        fun optionsSetting(
            @Nullable placeholder: Drawable,
            @Nullable error: Drawable
        ): RequestOptions {
            return RequestOptions()
                .placeholder(placeholder)
                .error(error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
        }

        /**
         * 圆角并兼容centerCrop
         */
        fun radiusAndCenterCrop(
            context: Context,
            url: String,
            options: RequestOptions,
            image: ImageView,
            radius: Int
        ) {
            Glide.with(context)
                .load(url)
                .apply(options.also { it.transform(GlideRoundTransform(context, radius)) })
                .into(image);
        }

        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circularSrcNone(context: Context, url: String, options: RequestOptions, iv: ImageView) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(object : BitmapImageViewTarget(iv) {
                    override fun setResource(resource: Bitmap?) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            iv.run {
                                setImageResource(R.drawable.none_24dp)
                                background = RoundedBitmapDrawableFactory
                                    .create(context.resources, resource).also {
                                        it.isCircular = true
                                    }
                            }
                        }
                    }
                })
        }

        /**
         * 圆形(显示在背景，前景为none)
         */
        fun circular(context: Context, url: String, options: RequestOptions, iv: ImageView) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(object : BitmapImageViewTarget(iv) {
                    override fun setResource(resource: Bitmap?) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            iv.run {
                                setImageDrawable(
                                    RoundedBitmapDrawableFactory
                                        .create(context.resources, resource).also {
                                            it.isCircular = true
                                        })
                            }
                        }
                    }
                })
        }

        /**
         * 圆角模糊
         */
        fun vagueAndRadius(view: ImageView, url: String?, radius: Float) {
            vagueAndRadius(view, url, radius, 1, R.drawable.none_24dp, Bitmap.Config.ARGB_4444)
        }

        /**
         * 圆角模糊
         */
        fun vagueAndRadius(view: ImageView, url: String?, radius: Float, vague: Int) {
            vagueAndRadius(view, url, radius, vague, R.drawable.none_24dp, Bitmap.Config.ARGB_4444)
        }

        /**
         * 圆角模糊
         */
        fun vagueAndRadius(
            view: ImageView,
            url: String?,
            radius: Float,
            vague: Int,
            errorDrawable: Int
        ) {
            vagueAndRadius(view, url, radius, vague, errorDrawable, Bitmap.Config.ARGB_4444)
        }

        /**
         * 圆角模糊
         * view  ->  imageView
         * url   ->  load img url
         * radius  ->  圆角大小，超过半数为圆形，vague越大，同等值的radius圆角度也越大
         * vague  ->  标准模糊的多少倍（标准的为1-25，vague为1时，模糊等级为25  , 设置参考   模糊度= 25 X vague）
         * errorDrawable -> 错误时的占位图
         * config  ->   参考Bitmap.Config的枚举
         */
        fun vagueAndRadius(
            view: ImageView, url: String?, radius: Float, vague: Int,
            errorDrawable: Int, config: Bitmap.Config
        ) {
            Glide.with(view.context)
                .asBitmap()
                .load(url)
                .apply(
                    RequestOptions.bitmapTransform(
                        GlideBlurTransformation(
                            view.context,
                            config,
                            vague
                        )
                    )
                )
                .into(object : BitmapImageViewTarget(view) {
                    override fun setResource(resource: Bitmap?) {
                        view.setImageDrawable(RoundedBitmapDrawableFactory
                            .create(view.context.resources, resource).also {
                                it.isCircular = true
                                it.cornerRadius = radius
                            })
                        super.setRequest(request)
                    }

                    override fun onLoadFailed(e: Drawable?) {
                        try {
                            view.setImageDrawable(
                                RoundedBitmapDrawableFactory.create(
                                    view.context.resources,
                                    BitmapUtils.drawableToBitmap(
                                        view.context.resources.getDrawable(errorDrawable)
                                    )
                                ).also {
                                    it.isCircular = true
                                    it.cornerRadius = radius
                                })
                        } catch (e: IllegalArgumentException) {
                        }
                        super.onLoadFailed(e)
                    }
                })
        }
    }

}
