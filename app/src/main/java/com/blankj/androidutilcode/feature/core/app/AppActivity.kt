package com.blankj.androidutilcode.feature.core.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.Config
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.helper.AssertHelper
import com.blankj.androidutilcode.helper.PermissionHelper
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : App 工具类 Demo
</pre> *
 */

class AppActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_app
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_app)

        findViewById<View>(R.id.btn_install_app).setOnClickListener(this)
        findViewById<View>(R.id.btn_install_app_silent).setOnClickListener(this)
        findViewById<View>(R.id.btn_uninstall_app).setOnClickListener(this)
        findViewById<View>(R.id.btn_uninstall_app_silent).setOnClickListener(this)
        findViewById<View>(R.id.btn_launch_app).setOnClickListener(this)
        findViewById<View>(R.id.btn_exit_app).setOnClickListener(this)
        findViewById<View>(R.id.btn_get_app_details_settings).setOnClickListener(this)
        val tvAboutApp = findViewById<TextView>(R.id.tv_about_app)
        tvAboutApp.text = SpanUtils()
                .append("app icon: ").appendImage(AppUtils.appIcon!!, SpanUtils.ALIGN_CENTER).appendLine()
                .appendLine(AppUtils.appInfo!!.toString())
                .appendLine("isAppRoot: " + AppUtils.isAppRoot)
                .appendLine("isAppDebug: " + AppUtils.isAppDebug)
                .appendLine("AppSignatureSHA1: " + AppUtils.appSignatureSHA1!!)
                .append("isAppForeground: " + AppUtils.isAppForeground)
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_install_app -> if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                PermissionHelper.requestStorage(
                        listener = object : PermissionHelper.OnPermissionGrantedListener {
                            override fun onPermissionGranted() {
                                AssertHelper.releaseInstallApk(object : AssertHelper.OnReleasedListener {
                                    override fun onReleased() {
                                        AppUtils.installApp(Config.TEST_APK_PATH,
                                                "com.blankj.androidutilcode.provider"
                                        )
                                    }
                                })
                            }
                        }
                )
            }
            R.id.btn_install_app_silent -> if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                ToastUtils.showShort(R.string.app_install_tips)
            } else {
                if (AppUtils.installAppSilent(Config.TEST_APK_PATH)) {
                    ToastUtils.showShort(R.string.install_successfully)
                } else {
                    ToastUtils.showShort(R.string.install_unsuccessfully)
                }
            }
            R.id.btn_uninstall_app -> if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                AppUtils.uninstallApp(Config.TEST_PKG)
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.btn_uninstall_app_silent -> if (AppUtils.isInstallApp(Config.TEST_PKG)) {
                if (AppUtils.uninstallAppSilent(Config.TEST_PKG, false)) {
                    ToastUtils.showShort(R.string.uninstall_successfully)
                } else {
                    ToastUtils.showShort(R.string.uninstall_unsuccessfully)
                }
            } else {
                ToastUtils.showShort(R.string.app_uninstall_tips)
            }
            R.id.btn_launch_app -> AppUtils.launchApp(this.packageName)
            R.id.btn_exit_app -> AppUtils.exitApp()
            R.id.btn_get_app_details_settings -> AppUtils.getAppDetailsSettings()
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, AppActivity::class.java)
            context.startActivity(starter)
        }
    }
}
