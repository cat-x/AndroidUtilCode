package com.blankj.androidutilcode.feature.core.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseActivity
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : Screen 工具类 Demo
</pre> *
 */
class ScreenActivity : BaseActivity() {

    internal lateinit var ivScreenshot: ImageView
    internal lateinit var tvAboutScreen: TextView

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_brightness
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        ivScreenshot = findViewById(R.id.iv_screenshot)
        tvAboutScreen = findViewById(R.id.tv_about_screen)
        findViewById<View>(R.id.btn_set_fullscreen).setOnClickListener(this)
        findViewById<View>(R.id.btn_set_landscape).setOnClickListener(this)
        findViewById<View>(R.id.btn_set_portrait).setOnClickListener(this)
        findViewById<View>(R.id.btn_screenshot).setOnClickListener(this)
        findViewById<View>(R.id.btn_set_sleep_duration).setOnClickListener(this)

        updateAboutScreen()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_set_fullscreen -> ScreenUtils.setFullScreen(this)
            R.id.btn_set_landscape -> ScreenUtils.setLandscape(this)
            R.id.btn_set_portrait -> ScreenUtils.setPortrait(this)
            R.id.btn_screenshot -> ivScreenshot.setImageBitmap(ScreenUtils.screenShot(this))
            R.id.btn_set_sleep_duration -> {
                ScreenUtils.sleepDuration = 100000
                updateAboutScreen()
            }
        }
    }

    private fun updateAboutScreen() {
        tvAboutScreen.text = SpanUtils()
                .appendLine("getScreenWidth: " + ScreenUtils.screenWidth)
                .appendLine("getScreenHeight: " + ScreenUtils.screenHeight)
                .appendLine("isLandscape: " + ScreenUtils.isLandscape)
                .appendLine("isPortrait: " + ScreenUtils.isPortrait)
                .appendLine("getScreenRotation: " + ScreenUtils.getScreenRotation(this))
                .appendLine("isScreenLock: " + ScreenUtils.isScreenLock)
                .appendLine("getSleepDuration: " + ScreenUtils.sleepDuration)
                .append("isTablet: " + ScreenUtils.isTablet)
                .create()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ScreenActivity::class.java)
            context.startActivity(starter)
        }
    }
}
