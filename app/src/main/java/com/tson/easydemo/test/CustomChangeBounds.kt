package com.tson.easydemo.test
import android.animation.Animator
import android.transition.ChangeBounds
import android.transition.TransitionValues
import android.view.ViewGroup
import android.view.animation.AnimationUtils

/**
 *  Date 2021/5/31 1:36 下午
 *
 * @author Tson
 */
class CustomChangeBounds : ChangeBounds() {

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator? {

        val changeBounds = super.createAnimator(sceneRoot, startValues, endValues)
        if (startValues == null || endValues == null || changeBounds == null) {
            return null
        }
        changeBounds.run {
            duration = 500
            interpolator = AnimationUtils.loadInterpolator(sceneRoot.context,
                android.R.interpolator.fast_out_slow_in)
        }
        return changeBounds
    }

}