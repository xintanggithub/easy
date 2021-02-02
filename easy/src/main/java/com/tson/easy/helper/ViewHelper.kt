package com.tson.easy.helper

import android.view.View
import android.view.ViewGroup
import com.tson.easy.activity.BeforeActivity

/**
 *  Date 2021/2/2 3:36 PM
 *
 * @author Tson
 */
object ViewHelper {
    fun looperQueryLoadingView(view: View): ViewGroup? {
        if (view is ViewGroup) {
            view.childCount.also {
                if (it > 0) {
                    for (n in 0 until it) {
                        val tempChildrenView = view.getChildAt(n)
                        if (tempChildrenView is ViewGroup) {
                            return if (BeforeActivity.LOADING_VIEW_TAG == tempChildrenView.tag) {
                                tempChildrenView
                            } else {
                                val result = looperQueryLoadingView(tempChildrenView)
                                result ?: continue
                            }
                        } else {
                            continue
                        }
                    }
                    return null
                } else {
                    return null
                }
            }
        } else {
            return null
        }
    }
}