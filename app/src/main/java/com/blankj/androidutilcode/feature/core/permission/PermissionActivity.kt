package com.blankj.androidutilcode.feature.core.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.helper.DialogHelper
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/01
 * desc  : Permission 工具类 Demo
</pre> *
 */
class PermissionActivity : BaseBackActivity() {

    internal lateinit var tvAboutPermission: TextView
    internal lateinit var permissions: String

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_permission
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_permission)

        tvAboutPermission = findViewById(R.id.tv_about_permission)
        findViewById<View>(R.id.btn_open_app_settings).setOnClickListener(this)
        findViewById<View>(R.id.btn_request_calendar).setOnClickListener(this)
        findViewById<View>(R.id.btn_request_record_audio).setOnClickListener(this)

        val sb = StringBuilder()
        for (s in PermissionUtils.permissions) {
            sb.append(s.substring(s.lastIndexOf('.') + 1)).append("\n")
        }
        permissions = sb.toString()
    }

    override fun onResume() {
        super.onResume()
        updateAboutPermission()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_open_app_settings -> PermissionUtils.openAppSettings()
            R.id.btn_request_calendar -> PermissionUtils.permission(PermissionConstants.CALENDAR)
                    .rationale(object : PermissionUtils.OnRationaleListener {
                        override fun rationale(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
                            DialogHelper.showRationaleDialog(shouldRequest)
                        }
                    })
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(permissionsGranted: List<String>) {
                            updateAboutPermission()
                            LogUtils.d(permissionsGranted)
                        }

                        override fun onDenied(permissionsDeniedForever: List<String>?,
                                              permissionsDenied: List<String>) {
                            if (!permissionsDeniedForever!!.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog()
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        }
                    })
                    .theme(object : PermissionUtils.ThemeCallback {
                        override fun onActivityCreate(activity: Activity) {
                            ScreenUtils.setFullScreen(activity)
                        }
                    })
                    .request()
            R.id.btn_request_record_audio -> PermissionUtils.permission(PermissionConstants.MICROPHONE)
                    .rationale(object : PermissionUtils.OnRationaleListener {
                        override fun rationale(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
                            DialogHelper.showRationaleDialog(shouldRequest)
                        }
                    })
                    .callback(object : PermissionUtils.FullCallback {
                        override fun onGranted(permissionsGranted: List<String>) {
                            updateAboutPermission()
                            LogUtils.d(permissionsGranted)
                        }

                        override fun onDenied(permissionsDeniedForever: List<String>?,
                                              permissionsDenied: List<String>) {
                            if (!permissionsDeniedForever!!.isEmpty()) {
                                DialogHelper.showOpenAppSettingDialog()
                            }
                            LogUtils.d(permissionsDeniedForever, permissionsDenied)
                        }
                    })
                    .request()
        }
    }

    private fun updateAboutPermission() {
        tvAboutPermission.text = SpanUtils()
                .append(permissions).setBold()
                .appendLine("READ_CALENDAR: " + PermissionUtils.isGranted(Manifest.permission.READ_CALENDAR))
                .appendLine("RECORD_AUDIO: " + PermissionUtils.isGranted(Manifest.permission.RECORD_AUDIO))
                .create()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, PermissionActivity::class.java)
            context.startActivity(starter)
        }
    }
}
