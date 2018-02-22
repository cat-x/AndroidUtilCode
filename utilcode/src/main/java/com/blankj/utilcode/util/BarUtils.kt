package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.v4.widget.DrawerLayout
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.LinearLayout

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/23
 * desc  : 栏相关工具类
</pre> *
 */
class BarUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        ///////////////////////////////////////////////////////////////////////////
        // status bar
        ///////////////////////////////////////////////////////////////////////////

        private val DEFAULT_ALPHA = 112
        private val TAG_COLOR = "TAG_COLOR"
        private val TAG_ALPHA = "TAG_ALPHA"
        private val TAG_OFFSET = -123

        /**
         * 获取状态栏高度（单位：px）
         *
         * @return 状态栏高度（单位：px）
         */
        val statusBarHeight: Int
            get() {
                val resources = Utils.app.resources
                val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
                return resources.getDimensionPixelSize(resourceId)
            }

        /**
         * 设置状态栏是否可见
         *
         * @param activity  activity
         * @param isVisible `true`: 可见<br></br>`false`: 不可见
         */
        fun setStatusBarVisibility(activity: Activity, isVisible: Boolean) {
            if (isVisible) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }

        /**
         * 判断状态栏是否可见
         *
         * @param activity activity
         * @return `true`: 可见<br></br>`false`: 不可见
         */
        fun isStatusBarVisible(activity: Activity): Boolean {
            val flags = activity.window.attributes.flags
            return flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == 0
        }

        /**
         * 设置状态栏是否为浅色模式
         *
         * @param activity    activity
         * @param isLightMode 是否为浅色模式
         */
        fun setStatusBarLightMode(activity: Activity, isLightMode: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window = activity.window
                val decorView = window.decorView
                if (decorView != null) {
                    var vis = decorView.systemUiVisibility
                    if (isLightMode) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    decorView.systemUiVisibility = vis
                }
            }
        }

        /**
         * 为 view 增加 MarginTop 为状态栏高度
         *
         * @param view view
         */
        fun addMarginTopEqualStatusBarHeight(view: View) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val haveSetOffset = view.getTag(TAG_OFFSET)
            if (haveSetOffset != null && haveSetOffset as Boolean) return
            val layoutParams = view.layoutParams as MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + statusBarHeight,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin)
            view.setTag(TAG_OFFSET, true)
        }

        /**
         * 为 view 减少 MarginTop 为状态栏高度
         *
         * @param view view
         */
        fun subtractMarginTopEqualStatusBarHeight(view: View) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val haveSetOffset = view.getTag(TAG_OFFSET)
            if (haveSetOffset == null || !(haveSetOffset as Boolean)) return
            val layoutParams = view.layoutParams as MarginLayoutParams
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin - statusBarHeight,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin)
            view.setTag(TAG_OFFSET, false)
        }

        /**
         * 设置状态栏颜色
         *
         * @param activity activity
         * @param color    状态栏颜色值
         * @param alpha    状态栏透明度，此透明度并非颜色中的透明度
         * @param isDecor  `true`: 设置在 DecorView 中<br></br>
         * `false`: 设置在 ContentView 中
         */
        @JvmOverloads
        fun setStatusBarColor(activity: Activity,
                              @ColorInt color: Int,
                              @IntRange(from = 0, to = 255) alpha: Int = DEFAULT_ALPHA,
                              isDecor: Boolean = false) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            hideAlphaView(activity)
            transparentStatusBar(activity)
            addStatusBarColor(activity, color, alpha, isDecor)
        }

        /**
         * 设置状态栏透明度
         *
         * @param activity activity
         * @param alpha    状态栏透明度
         * @param isDecor  `true`: 设置在 DecorView 中<br></br>
         * `false`: 设置在 ContentView 中
         */
        @JvmOverloads
        fun setStatusBarAlpha(activity: Activity,
                              @IntRange(from = 0, to = 255) alpha: Int = DEFAULT_ALPHA,
                              isDecor: Boolean = false) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            hideColorView(activity)
            transparentStatusBar(activity)
            addStatusBarAlpha(activity, alpha, isDecor)
        }

        /**
         * 设置状态栏颜色
         *
         * @param fakeStatusBar 伪造状态栏
         * @param color         状态栏颜色值
         * @param alpha         状态栏透明度，此透明度并非颜色中的透明度
         */
        @JvmOverloads
        fun setStatusBarColor(fakeStatusBar: View,
                              @ColorInt color: Int,
                              @IntRange(from = 0, to = 255) alpha: Int = DEFAULT_ALPHA) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            fakeStatusBar.visibility = View.VISIBLE
            transparentStatusBar(fakeStatusBar.context as Activity)
            val layoutParams = fakeStatusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = BarUtils.statusBarHeight
            fakeStatusBar.setBackgroundColor(getStatusBarColor(color, alpha))
        }

        /**
         * 设置状态栏透明度
         *
         * @param fakeStatusBar 伪造状态栏
         * @param alpha         状态栏透明度
         */
        @JvmOverloads
        fun setStatusBarAlpha(fakeStatusBar: View,
                              @IntRange(from = 0, to = 255) alpha: Int = DEFAULT_ALPHA) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            fakeStatusBar.visibility = View.VISIBLE
            transparentStatusBar(fakeStatusBar.context as Activity)
            val layoutParams = fakeStatusBar.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = BarUtils.statusBarHeight
            fakeStatusBar.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
        }

        /**
         * 为 DrawerLayout 设置状态栏颜色
         *
         * DrawLayout 需设置 `android:fitsSystemWindows="true"`
         *
         * @param activity      activity
         * @param drawer        drawerLayout
         * @param fakeStatusBar 伪造状态栏
         * @param color         状态栏颜色值
         * @param isTop         drawerLayout 是否在顶层
         */
        fun setStatusBarColor4Drawer(activity: Activity,
                                     drawer: DrawerLayout,
                                     fakeStatusBar: View,
                                     @ColorInt color: Int,
                                     isTop: Boolean) {
            setStatusBarColor4Drawer(activity, drawer, fakeStatusBar, color, DEFAULT_ALPHA, isTop)
        }

        /**
         * 为 DrawerLayout 设置状态栏颜色
         *
         * DrawLayout 需设置 `android:fitsSystemWindows="true"`
         *
         * @param activity      activity
         * @param drawer        drawerLayout
         * @param fakeStatusBar 伪造状态栏
         * @param color         状态栏颜色值
         * @param alpha         状态栏透明度，此透明度并非颜色中的透明度
         * @param isTop         drawerLayout 是否在顶层
         */
        fun setStatusBarColor4Drawer(activity: Activity,
                                     drawer: DrawerLayout,
                                     fakeStatusBar: View,
                                     @ColorInt color: Int,
                                     @IntRange(from = 0, to = 255) alpha: Int,
                                     isTop: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            drawer.fitsSystemWindows = false
            transparentStatusBar(activity)
            setStatusBarColor(fakeStatusBar, color, if (isTop) alpha else 0)
            var i = 0
            val len = drawer.childCount
            while (i < len) {
                drawer.getChildAt(i).fitsSystemWindows = false
                i++
            }
            if (isTop) {
                hideAlphaView(activity)
            } else {
                addStatusBarAlpha(activity, alpha, false)
            }
        }

        /**
         * 为 DrawerLayout 设置状态栏透明度
         *
         * DrawLayout 需设置 `android:fitsSystemWindows="true"`
         *
         * @param activity      activity
         * @param drawer        drawerLayout
         * @param fakeStatusBar 伪造状态栏
         * @param isTop         drawerLayout 是否在顶层
         */
        fun setStatusBarAlpha4Drawer(activity: Activity,
                                     drawer: DrawerLayout,
                                     fakeStatusBar: View,
                                     isTop: Boolean) {
            setStatusBarAlpha4Drawer(activity, drawer, fakeStatusBar, DEFAULT_ALPHA, isTop)
        }

        /**
         * 为 DrawerLayout 设置状态栏透明度
         *
         * DrawLayout 需设置 `android:fitsSystemWindows="true"`
         *
         * @param activity      activity
         * @param drawer        drawerLayout
         * @param fakeStatusBar 伪造状态栏
         * @param alpha         状态栏透明度
         * @param isTop         drawerLayout 是否在顶层
         */
        fun setStatusBarAlpha4Drawer(activity: Activity,
                                     drawer: DrawerLayout,
                                     fakeStatusBar: View,
                                     @IntRange(from = 0, to = 255) alpha: Int,
                                     isTop: Boolean) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            drawer.fitsSystemWindows = false
            transparentStatusBar(activity)
            setStatusBarAlpha(fakeStatusBar, if (isTop) alpha else 0)
            var i = 0
            val len = drawer.childCount
            while (i < len) {
                drawer.getChildAt(i).fitsSystemWindows = false
                i++
            }
            if (isTop) {
                hideAlphaView(activity)
            } else {
                addStatusBarAlpha(activity, alpha, false)
            }
        }

        private fun addStatusBarColor(activity: Activity,
                                      color: Int,
                                      alpha: Int,
                                      isDecor: Boolean) {
            val parent = if (isDecor)
                activity.window.decorView as ViewGroup
            else
                activity.findViewById<View>(android.R.id.content) as ViewGroup
            val fakeStatusBarView = parent.findViewWithTag<View>(TAG_COLOR)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(getStatusBarColor(color, alpha))
            } else {
                parent.addView(createColorStatusBarView(parent.context, color, alpha))
            }
        }

        private fun addStatusBarAlpha(activity: Activity,
                                      alpha: Int,
                                      isDecor: Boolean) {
            val parent = if (isDecor)
                activity.window.decorView as ViewGroup
            else
                activity.findViewById<View>(android.R.id.content) as ViewGroup
            val fakeStatusBarView = parent.findViewWithTag<View>(TAG_ALPHA)
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.visibility == View.GONE) {
                    fakeStatusBarView.visibility = View.VISIBLE
                }
                fakeStatusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            } else {
                parent.addView(createAlphaStatusBarView(parent.context, alpha))
            }
        }

        private fun hideColorView(activity: Activity) {
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_COLOR) ?: return
            fakeStatusBarView.visibility = View.GONE
        }

        private fun hideAlphaView(activity: Activity) {
            val decorView = activity.window.decorView as ViewGroup
            val fakeStatusBarView = decorView.findViewWithTag<View>(TAG_ALPHA) ?: return
            fakeStatusBarView.visibility = View.GONE
        }

        private fun getStatusBarColor(color: Int, alpha: Int): Int {
            if (alpha == 0) return color
            val a = 1 - alpha / 255f
            var red = color shr 16 and 0xff
            var green = color shr 8 and 0xff
            var blue = color and 0xff
            red = (red * a + 0.5).toInt()
            green = (green * a + 0.5).toInt()
            blue = (blue * a + 0.5).toInt()
            return Color.argb(255, red, green, blue)
        }

        /**
         * 绘制一个和状态栏一样高的颜色矩形
         */
        private fun createColorStatusBarView(context: Context,
                                             color: Int,
                                             alpha: Int): View {
            val statusBarView = View(context)
            statusBarView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            statusBarView.setBackgroundColor(getStatusBarColor(color, alpha))
            statusBarView.tag = TAG_COLOR
            return statusBarView
        }

        /**
         * 绘制一个和状态栏一样高的黑色透明度矩形
         */
        private fun createAlphaStatusBarView(context: Context, alpha: Int): View {
            val statusBarView = View(context)
            statusBarView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight)
            statusBarView.setBackgroundColor(Color.argb(alpha, 0, 0, 0))
            statusBarView.tag = TAG_ALPHA
            return statusBarView
        }

        private fun transparentStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return
            val window = activity.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                val option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.decorView.systemUiVisibility = option
                window.statusBarColor = Color.TRANSPARENT
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // action bar
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 获取 ActionBar 高度
         *
         * @param activity activity
         * @return ActionBar 高度
         */
        fun getActionBarHeight(activity: Activity): Int {
            val tv = TypedValue()
            return if (activity.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                TypedValue.complexToDimensionPixelSize(
                        tv.data, activity.resources.displayMetrics
                )
            } else 0
        }

        ///////////////////////////////////////////////////////////////////////////
        // notification bar
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 设置通知栏是否可见
         *
         * 需添加权限
         * `<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />`
         *
         * @param isVisible `true`: 可见<br></br>`false`: 关闭
         */
        fun setNotificationBarVisibility(isVisible: Boolean) {
            val methodName: String
            if (isVisible) {
                methodName = if (Build.VERSION.SDK_INT <= 16) "expand" else "expandNotificationsPanel"
            } else {
                methodName = if (Build.VERSION.SDK_INT <= 16) "collapse" else "collapsePanels"
            }
            invokePanels(methodName)
        }

        @SuppressLint("PrivateApi", "WrongConstant")
        private fun invokePanels(methodName: String) {
            try {
                val service = Utils.app.getSystemService("statusbar")
                val statusBarManager = Class.forName("android.app.StatusBarManager")
                val expand = statusBarManager.getMethod(methodName)
                expand.invoke(service)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        ///////////////////////////////////////////////////////////////////////////
        // navigation bar
        ///////////////////////////////////////////////////////////////////////////

        /**
         * 获取导航栏高度
         *
         * @return 导航栏高度
         */
        val navBarHeight: Int
            get() {
                val res = Utils.app.resources
                val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
                return if (resourceId != 0) {
                    res.getDimensionPixelSize(resourceId)
                } else {
                    0
                }
            }

        /**
         * 设置导航栏是否可见
         *
         * @param activity  activity
         * @param isVisible `true`: 可见<br></br>`false`: 不可见
         */
        fun setNavBarVisibility(activity: Activity, isVisible: Boolean) {
            if (isVisible) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            } else {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                val decorView = activity.window.decorView
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val visibility = decorView.systemUiVisibility
                    decorView.systemUiVisibility = visibility and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
                }
            }
        }

        /**
         * 设置导航栏沉浸式
         *
         * @param activity activity
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun setNavBarImmersive(activity: Activity) {
            val decorView = activity.window.decorView
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.systemUiVisibility = uiOptions
        }

        /**
         * 判断导航栏是否可见
         *
         * @param activity activity
         * @return `true`: 可见<br></br>`false`: 不可见
         */
        fun isNavBarVisible(activity: Activity): Boolean {
            val isNoLimits = activity.window.attributes.flags and WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS != 0
            if (isNoLimits) return false
            val decorView = activity.window.decorView
            val visibility = decorView.systemUiVisibility
            return visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION == 0
        }
    }
}
