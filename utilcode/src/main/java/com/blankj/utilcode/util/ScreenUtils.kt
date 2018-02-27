package com.blankj.utilcode.util

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Build
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Surface
import android.view.WindowManager

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 屏幕相关工具类
</pre> *
 */
object ScreenUtils {


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    val screenWidth: Int
        get() {
            val wm = Utils.app.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                    ?: return Utils.app.resources.displayMetrics.widthPixels
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.x
        }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    val screenHeight: Int
        get() {
            val wm = Utils.app.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                    ?: return Utils.app.resources.displayMetrics.heightPixels
            val point = Point()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                wm.defaultDisplay.getRealSize(point)
            } else {
                wm.defaultDisplay.getSize(point)
            }
            return point.y
        }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    val screenDensity: Float
        get() = Utils.app.resources.displayMetrics.density

    /**
     * 获取屏幕密度 DPI
     *
     * @return 屏幕密度 DPI
     */
    val screenDensityDpi: Int
        get() = Utils.app.resources.displayMetrics.densityDpi

    /**
     * 设置屏幕为全屏
     *
     * @param activity activity
     */
    fun setFullScreen(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    /**
     * 设置屏幕为横屏
     *
     * 还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"
     *
     * 不设置 Activity 的 android:configChanges 时，
     * 切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次
     *
     * 设置 Activity 的 android:configChanges="orientation"时，
     * 切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次
     *
     * 设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"
     * （4.0 以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行 onConfigurationChanged 方法
     *
     * @param activity activity
     */
    fun setLandscape(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    fun setPortrait(activity: Activity) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 判断是否横屏
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isLandscape: Boolean
        get() = Utils.app.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    /**
     * 判断是否竖屏
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isPortrait: Boolean
        get() = Utils.app.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    fun getScreenRotation(activity: Activity): Int {
        when (activity.windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_0 -> return 0
            Surface.ROTATION_90 -> return 90
            Surface.ROTATION_180 -> return 180
            Surface.ROTATION_270 -> return 270
            else -> return 0
        }
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    @JvmOverloads
    fun screenShot(activity: Activity, isDeleteStatusBar: Boolean = false): Bitmap {
        val decorView = activity.window.decorView
        decorView.isDrawingCacheEnabled = true
        decorView.buildDrawingCache()
        val bmp = decorView.drawingCache
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        val ret: Bitmap
        if (isDeleteStatusBar) {
            val resources = activity.resources
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            val statusBarHeight = resources.getDimensionPixelSize(resourceId)
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            )
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels)
        }
        decorView.destroyDrawingCache()
        return ret
    }

    /**
     * 判断是否锁屏
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isScreenLock: Boolean
        get() {
            val km = Utils.app.getSystemService(Context.KEYGUARD_SERVICE) as? KeyguardManager
            return km != null && km.inKeyguardRestrictedInputMode()
        }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    /**
     * 设置进入休眠时长
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_SETTINGS" />`
     *
     * @param duration 时长
     */
    var sleepDuration: Int
        get() {
            try {
                return Settings.System.getInt(
                        Utils.app.contentResolver,
                        Settings.System.SCREEN_OFF_TIMEOUT
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                return -123
            }

        }
        set(duration) {
            Settings.System.putInt(
                    Utils.app.contentResolver,
                    Settings.System.SCREEN_OFF_TIMEOUT,
                    duration
            )
        }

    /**
     * 判断是否是平板
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isTablet: Boolean
        get() = Utils.app.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
}

/**
 * 截屏
 *
 * @param activity activity
 * @return Bitmap
 */
