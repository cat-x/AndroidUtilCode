package com.blankj.androidutilcode.feature.core.phone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.helper.PermissionHelper
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Phone 工具类 Demo
</pre> *
 */
class PhoneActivity : BaseBackActivity() {

    private var tvAboutPhone: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_phone
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.setTitle(getString(R.string.demo_phone))

        findViewById<View>(R.id.btn_dial).setOnClickListener(this)
        findViewById<View>(R.id.btn_call).setOnClickListener(this)
        findViewById<View>(R.id.btn_send_sms).setOnClickListener(this)
        findViewById<View>(R.id.btn_send_sms_silent).setOnClickListener(this)
        tvAboutPhone = findViewById(R.id.tv_about_phone)

        PermissionHelper.requestPhone(
                object : PermissionHelper.OnPermissionGrantedListener {
                    override fun onPermissionGranted() {
                        tvAboutPhone!!.text = SpanUtils()
                                .appendLine("isPhone: " + PhoneUtils.isPhone)
                                .appendLine("getIMEI: " + PhoneUtils.imei)
                                .appendLine("getIMSI: " + PhoneUtils.imsi)
                                .appendLine("getPhoneType: " + PhoneUtils.phoneType)
                                .appendLine("isSimCardReady: " + PhoneUtils.isSimCardReady)
                                .appendLine("getSimOperatorName: " + PhoneUtils.simOperatorName!!)
                                .appendLine("getSimOperatorByMnc: " + PhoneUtils.simOperatorByMnc!!)
                                .appendLine("getPhoneStatus: " + PhoneUtils.phoneStatus)
                                .create()
                    }
                }, object : PermissionHelper.OnPermissionDeniedListener {
            override fun onPermissionDenied() {
                tvAboutPhone!!.text = SpanUtils()
                        .appendLine("isPhone: " + PhoneUtils.isPhone)
                        .appendLine("getIMEI: " + "need permission")
                        .appendLine("getIMSI: " + "need permission")
                        .appendLine("getPhoneType: " + PhoneUtils.phoneType)
                        .appendLine("isSimCardReady: " + PhoneUtils.isSimCardReady)
                        .appendLine("getSimOperatorName: " + PhoneUtils.simOperatorName!!)
                        .appendLine("getSimOperatorByMnc: " + PhoneUtils.simOperatorByMnc!!)
                        .appendLine("getPhoneStatus: " + "need permission")
                        .create()
            }
        }
        )
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_dial -> PhoneUtils.dial("10000")
            R.id.btn_call -> PermissionHelper.requestPhone(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    PhoneUtils.call("10000")
                }

            })
            R.id.btn_send_sms -> PhoneUtils.sendSms("10000", "sendSms")
            R.id.btn_send_sms_silent -> PermissionHelper.requestSms(object : PermissionHelper.OnPermissionGrantedListener {
                override fun onPermissionGranted() {
                    PhoneUtils.sendSmsSilent("10000", "sendSmsSilent")
                }
            })
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, PhoneActivity::class.java)
            context.startActivity(starter)
        }
    }
}
