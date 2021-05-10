package com.tson.utils.act

import android.app.Activity

/**
 *  Date 2020-10-26 15:05
 *
 * @author Tson
 */
class ActivityUtils {
    companion object {
        private const val TAG = "ActivityUtils"
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { ActivityUtils() }
    }

    private val activityArray = hashMapOf<Int, Activity>()

    fun addActivity(act: Activity) {
        act.hashCode().also {
            if (!activityArray.containsKey(it)) {
                activityArray[act.hashCode()] = act
            }
        }
    }

    fun finishByClz(clz: Class<*>) {
        activityArray.forEach { (_, u) ->
            if (clz::class.java.name == u::class.java.name) {
                u.finish()
            }
        }
    }

    fun remove(act: Activity) {
        act.hashCode().also {
            if (activityArray.containsKey(it)) {
                activityArray.remove(it)
            }
        }
    }

    fun closeAll() {
        for (mutableEntry in activityArray) {
            try {
                mutableEntry.value.finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun clear() {
        activityArray.clear()
    }

}