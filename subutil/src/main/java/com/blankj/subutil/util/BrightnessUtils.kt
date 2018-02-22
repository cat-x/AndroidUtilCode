package com.blankj.subutil.util

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.annotation.IntRange
import android.support.annotation.RequiresApi
import android.view.Window

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/02/08
 * desc  : 亮度相关工具类
</pre> *
 */
object BrightnessUtils {

    /**
     * 判断是否开启自动调节亮度
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    val isAutoBrightnessEnabled: Boolean
        get() {
            return try {
                val mode = Settings.System.getInt(
                        Utils.app.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE
                )
                mode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                false
            }

        }


    /**
     * 设置是否开启自动调节亮度
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_SETTINGS" />`
     * 并得到授权
     *
     * @param enabled `true`: 打开<br></br>`false`: 关闭
     * @return `true`: 成功<br></br>`false`: 失败
     */
//    @RequiresPermission(android.Manifest.permission.WRITE_SETTINGS)
    @RequiresApi(Build.VERSION_CODES.M)
    fun setAutoBrightnessEnabled(enabled: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(Utils.app)) {
            val intent = Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:" + Utils.app.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            Utils.app.startActivity(intent)
            return false
        }
        return Settings.System.putInt(
                Utils.app.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                if (enabled)
                    Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                else
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
    }

    /**
     * 获取或设置屏幕亮度(brightness)
     *
     * @return 屏幕亮度 0-255
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_SETTINGS" />`
     * 并得到授权
     */
    var brightness: Int
        get() {
            return try {
                Settings.System.getInt(
                        Utils.app.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS
                )
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
                0
            }

        }
        set(@IntRange(from = 0, to = 255) brightness) {
            val resolver = Utils.app.contentResolver
            Settings.System.putInt(resolver, Settings.System.SCREEN_BRIGHTNESS, brightness)
            resolver.notifyChange(Settings.System.getUriFor("screen_brightness"), null)
        }

    /**
     * 设置窗口亮度
     *
     * @param window     窗口
     * @param brightness 亮度值
     */
    fun setWindowBrightness(window: Window,
                            @IntRange(from = 0, to = 255) brightness: Int) {
        val lp = window.attributes
        lp.screenBrightness = brightness / 255f
        window.attributes = lp
    }

    /**
     * 获取窗口亮度
     *
     * @param window 窗口
     * @return 屏幕亮度 0-255
     */
    fun getWindowBrightness(window: Window): Int {
        val lp = window.attributes
        val brightness = lp.screenBrightness
        return if (brightness < 0) brightness.toInt() else (brightness * 255).toInt()
    }
}
