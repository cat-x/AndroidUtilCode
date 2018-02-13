package com.blankj.androidutilcode.feature.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.feature.core.activity.ActivityActivity
import com.blankj.androidutilcode.feature.core.app.AppActivity
import com.blankj.androidutilcode.feature.core.bar.BarActivity
import com.blankj.androidutilcode.feature.core.clean.CleanActivity
import com.blankj.androidutilcode.feature.core.device.DeviceActivity
import com.blankj.androidutilcode.feature.core.fragment.FragmentActivity
import com.blankj.androidutilcode.feature.core.image.ImageActivity
import com.blankj.androidutilcode.feature.core.keyboard.KeyboardActivity
import com.blankj.androidutilcode.feature.core.log.LogActivity
import com.blankj.androidutilcode.feature.core.network.NetworkActivity
import com.blankj.androidutilcode.feature.core.permission.PermissionActivity
import com.blankj.androidutilcode.feature.core.phone.PhoneActivity
import com.blankj.androidutilcode.feature.core.process.ProcessActivity
import com.blankj.androidutilcode.feature.core.reflect.ReflectActivity
import com.blankj.androidutilcode.feature.core.screen.ScreenActivity
import com.blankj.androidutilcode.feature.core.sdcard.SDCardActivity
import com.blankj.androidutilcode.feature.core.snackbar.SnackbarActivity
import com.blankj.androidutilcode.feature.core.sp.SPActivity
import com.blankj.androidutilcode.feature.core.span.SpanActivity
import com.blankj.androidutilcode.feature.core.toast.ToastActivity

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
</pre> *
 */
class CoreUtilActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_util_core
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.core_util)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    fun coreUtilClick(view: View) {

    }

    fun activityClick(view: View) {
        ActivityActivity.start(this)
    }

    fun appClick(view: View) {
        AppActivity.start(this)
    }

    fun barClick(view: View) {
        BarActivity.start(this)
    }

    fun cleanClick(view: View) {
        CleanActivity.start(this)
    }

    fun crashClick(view: View) {
        throw NullPointerException("crash test")
    }

    fun deviceClick(view: View) {
        DeviceActivity.start(this)
    }

    fun fragmentClick(view: View) {
        FragmentActivity.start(this)
    }

    fun imageClick(view: View) {
        ImageActivity.start(this)
    }

    fun keyboardClick(view: View) {
        KeyboardActivity.start(this)
    }

    fun logClick(view: View) {
        LogActivity.start(this)
    }

    fun networkClick(view: View) {
        NetworkActivity.start(this)
    }

    fun permissionClick(view: View) {
        PermissionActivity.start(this)
    }

    fun phoneClick(view: View) {
        PhoneActivity.start(this)
    }

    fun processClick(view: View) {
        ProcessActivity.start(this)
    }

    fun reflectClick(view: View) {
        ReflectActivity.start(this)
    }

    fun screenClick(view: View) {
        ScreenActivity.start(this)
    }

    fun sdcardClick(view: View) {
        SDCardActivity.start(this)
    }

    fun snackbarClick(view: View) {
        SnackbarActivity.start(this)
    }

    fun spClick(view: View) {
        SPActivity.start(this)
    }

    fun spannableClick(view: View) {
        SpanActivity.start(this)
    }

    fun toastClick(view: View) {
        ToastActivity.start(this)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, CoreUtilActivity::class.java)
            context.startActivity(starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}
