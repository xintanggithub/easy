package com.tson.view.status

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout.LayoutParams

/**
 * Date 2019/5/30 7:35 PM
 *
 * @author tangxin
 */
class SystemBarTintManager
@SuppressLint("ResourceType")
@TargetApi(19)
constructor(activity: Activity) {

    val config: SystemBarConfig
    private var mStatusBarAvailable: Boolean = false
    private var mNavBarAvailable: Boolean = false
    var isStatusBarTintEnabled: Boolean = false
        set(enabled) {
            field = enabled
            if (mStatusBarAvailable) {
                mStatusBarTintView!!.visibility = if (enabled) View.VISIBLE else View.GONE
            }
        }
    var isNavBarTintEnabled: Boolean = false
        private set
    private var mStatusBarTintView: View? = null
    private var mNavBarTintView: View? = null

    init {

        val win = activity.window
        val decorViewGroup = win.decorView as ViewGroup

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val attrs = intArrayOf(
                android.R.attr.windowTranslucentStatus,
                android.R.attr.windowTranslucentNavigation
            )
            val a = activity.obtainStyledAttributes(attrs)
            try {
                mStatusBarAvailable = a.getBoolean(0, false)
                mNavBarAvailable = a.getBoolean(1, false)
            } finally {
                a.recycle()
            }

            val winParams = win.attributes
            var bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            if (winParams.flags and bits != 0) {
                mStatusBarAvailable = true
            }
            bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            if (winParams.flags and bits != 0) {
                mNavBarAvailable = true
            }
        }

        config = SystemBarConfig(activity, mStatusBarAvailable, mNavBarAvailable)
        if (!config.hasNavigtionBar()) {
            mNavBarAvailable = false
        }

        if (mStatusBarAvailable) {
            setupStatusBarView(activity, decorViewGroup)
        }
        if (mNavBarAvailable) {
            setupNavBarView(activity, decorViewGroup)
        }

    }

    fun setNavigationBarTintEnabled(enabled: Boolean) {
        isNavBarTintEnabled = enabled
        if (mNavBarAvailable) {
            mNavBarTintView!!.visibility = if (enabled) View.VISIBLE else View.GONE
        }
    }

    fun setTintColor(color: Int) {
        setStatusBarTintColor(color)
        setNavigationBarTintColor(color)
    }

    fun setTintResource(res: Int) {
        setStatusBarTintResource(res)
        setNavigationBarTintResource(res)
    }

    fun setTintDrawable(drawable: Drawable) {
        setStatusBarTintDrawable(drawable)
        setNavigationBarTintDrawable(drawable)
    }

    fun setTintAlpha(alpha: Float) {
        setStatusBarAlpha(alpha)
        setNavigationBarAlpha(alpha)
    }

    fun setStatusBarTintColor(color: Int) {
        if (mStatusBarAvailable) {
            mStatusBarTintView!!.setBackgroundColor(color)
        }
    }

    fun setStatusBarTintResource(res: Int) {
        if (mStatusBarAvailable) {
            mStatusBarTintView!!.setBackgroundResource(res)
        }
    }

    fun setStatusBarTintDrawable(drawable: Drawable) {
        if (mStatusBarAvailable) {
            mStatusBarTintView!!.setBackgroundDrawable(drawable)
        }
    }

    @TargetApi(11)
    fun setStatusBarAlpha(alpha: Float) {
        if (mStatusBarAvailable) {
            mStatusBarTintView!!.alpha = alpha
        }
    }

    fun setNavigationBarTintColor(color: Int) {
        if (mNavBarAvailable) {
            mNavBarTintView!!.setBackgroundColor(color)
        }
    }

    fun setNavigationBarTintResource(res: Int) {
        if (mNavBarAvailable) {
            mNavBarTintView!!.setBackgroundResource(res)
        }
    }

    fun setNavigationBarTintDrawable(drawable: Drawable) {
        if (mNavBarAvailable) {
            mNavBarTintView!!.setBackgroundDrawable(drawable)
        }
    }

    @TargetApi(11)
    fun setNavigationBarAlpha(alpha: Float) {
        if (mNavBarAvailable) {
            mNavBarTintView!!.alpha = alpha
        }
    }

    private fun setupStatusBarView(context: Context, decorViewGroup: ViewGroup) {
        mStatusBarTintView = View(context)
        val params = LayoutParams(LayoutParams.MATCH_PARENT, config.statusBarHeight)
        params.gravity = Gravity.TOP
        if (mNavBarAvailable && !config.isNavigationAtBottom) {
            params.rightMargin = config.navigationBarWidth
        }
        mStatusBarTintView!!.layoutParams = params
        mStatusBarTintView!!.setBackgroundColor(DEFAULT_TINT_COLOR)
        mStatusBarTintView!!.visibility = View.GONE
        decorViewGroup.addView(mStatusBarTintView)
    }

    private fun setupNavBarView(context: Context, decorViewGroup: ViewGroup) {
        mNavBarTintView = View(context)
        val params: LayoutParams
        if (config.isNavigationAtBottom) {
            params = LayoutParams(LayoutParams.MATCH_PARENT, config.navigationBarHeight)
            params.gravity = Gravity.BOTTOM
        } else {
            params = LayoutParams(config.navigationBarWidth, LayoutParams.MATCH_PARENT)
            params.gravity = Gravity.RIGHT
        }
        mNavBarTintView!!.layoutParams = params
        mNavBarTintView!!.setBackgroundColor(DEFAULT_TINT_COLOR)
        mNavBarTintView!!.visibility = View.GONE
        decorViewGroup.addView(mNavBarTintView)
    }

    class SystemBarConfig constructor(
        activity: Activity,
        private val mTranslucentStatusBar: Boolean,
        private val mTranslucentNavBar: Boolean
    ) {

        val statusBarHeight: Int

        val actionBarHeight: Int
        private val mHasNavigationBar: Boolean

        val navigationBarHeight: Int

        val navigationBarWidth: Int
        private val mInPortrait: Boolean
        private val mSmallestWidthDp: Float

        val isNavigationAtBottom: Boolean
            get() = mSmallestWidthDp >= 600 || mInPortrait

        val pixelInsetBottom: Int
            get() = if (mTranslucentNavBar && isNavigationAtBottom) {
                navigationBarHeight
            } else {
                0
            }

        val pixelInsetRight: Int
            get() = if (mTranslucentNavBar && !isNavigationAtBottom) {
                navigationBarWidth
            } else {
                0
            }

        init {
            val res = activity.resources
            mInPortrait = res.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            mSmallestWidthDp = getSmallestWidthDp(activity)
            statusBarHeight = getInternalDimensionSize(res, STATUS_BAR_HEIGHT_RES_NAME)
            actionBarHeight = getActionBarHeight(activity)
            navigationBarHeight = getNavigationBarHeight(activity)
            navigationBarWidth = getNavigationBarWidth(activity)
            mHasNavigationBar = navigationBarHeight > 0
        }

        @TargetApi(14)
        private fun getActionBarHeight(context: Context): Int {
            var result = 0
            val tv = TypedValue()
            context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
            result =
                TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
            return result
        }

        @TargetApi(14)
        private fun getNavigationBarHeight(context: Context): Int {
            val res = context.resources
            val result = 0
            if (hasNavBar(context)) {
                val key: String
                if (mInPortrait) {
                    key = NAV_BAR_HEIGHT_RES_NAME
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME
                }
                return getInternalDimensionSize(res, key)
            }
            return result
        }

        @TargetApi(14)
        private fun getNavigationBarWidth(context: Context): Int {
            val res = context.resources
            val result = 0
            return if (hasNavBar(context)) {
                getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME)
            } else result
        }

        @TargetApi(14)
        private fun hasNavBar(context: Context): Boolean {
            val res = context.resources
            val resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android")
            if (resourceId != 0) {
                var hasNav = res.getBoolean(resourceId)
                // check override flag (see static block)
                if ("1" == sNavBarOverride) {
                    hasNav = false
                } else if ("0" == sNavBarOverride) {
                    hasNav = true
                }
                return hasNav
            } else {
                return !ViewConfiguration.get(context).hasPermanentMenuKey()
            }
        }

        private fun getInternalDimensionSize(res: Resources, key: String): Int {
            var result = 0
            val resourceId = res.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
            return result
        }

        @SuppressLint("NewApi")
        private fun getSmallestWidthDp(activity: Activity): Float {
            val metrics = DisplayMetrics()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.windowManager.defaultDisplay.getRealMetrics(metrics)
            } else {
                activity.windowManager.defaultDisplay.getMetrics(metrics)
            }
            val widthDp = metrics.widthPixels / metrics.density
            val heightDp = metrics.heightPixels / metrics.density
            return Math.min(widthDp, heightDp)
        }

        fun hasNavigtionBar(): Boolean {
            return mHasNavigationBar
        }

        fun getPixelInsetTop(withActionBar: Boolean): Int {
            return (if (mTranslucentStatusBar) statusBarHeight else 0) + if (withActionBar) actionBarHeight else 0
        }

        companion object {

            private val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"
            private val NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height"
            private val NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape"
            private val NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width"
            private val SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar"
        }

    }

    companion object {


        val DEFAULT_TINT_COLOR = -0x67000000

        private var sNavBarOverride: String? = null

        init {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val m = c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
                } catch (e: Throwable) {
                    sNavBarOverride = null
                }

            }
        }
    }

}
