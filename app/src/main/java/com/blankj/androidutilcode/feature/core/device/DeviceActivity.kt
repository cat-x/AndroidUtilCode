package com.blankj.androidutilcode.feature.core.device

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog : http://blankj.com
 * time : 2016/09/27
 * desc : Device 工具类 Demo
</pre> *
 */
class DeviceActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_device
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.setTitle(getString(R.string.demo_device))

        findViewById<View>(R.id.btn_shutdown).setOnClickListener(this)
        findViewById<View>(R.id.btn_reboot).setOnClickListener(this)
        findViewById<View>(R.id.btn_reboot_to_recovery).setOnClickListener(this)
        findViewById<View>(R.id.btn_reboot_to_bootloader).setOnClickListener(this)
        val tvAboutDevice = findViewById<TextView>(R.id.tv_about_device)
        tvAboutDevice.text = SpanUtils()
                .appendLine("isRoot: " + DeviceUtils.isDeviceRooted)
                .appendLine("getSDKVersionName: " + DeviceUtils.sdkVersionName)
                .appendLine("getSDKVersionCode: " + DeviceUtils.sdkVersionCode)
                .appendLine("getAndroidID: " + DeviceUtils.androidID)
                .appendLine("getMacAddress: " + DeviceUtils.macAddress)
                .appendLine("getManufacturer: " + DeviceUtils.manufacturer)
                .append("getModel: " + DeviceUtils.model)
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_shutdown -> DeviceUtils.shutdown()
            R.id.btn_reboot -> DeviceUtils.reboot()
            R.id.btn_reboot_to_recovery -> DeviceUtils.reboot2Recovery()
            R.id.btn_reboot_to_bootloader -> DeviceUtils.reboot2Bootloader()
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, DeviceActivity::class.java)
            context.startActivity(starter)
        }
    }
}
