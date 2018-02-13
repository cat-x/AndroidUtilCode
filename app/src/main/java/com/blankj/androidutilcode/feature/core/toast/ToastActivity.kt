package com.blankj.androidutilcode.feature.core.toast

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : Toast 工具类 Demo
</pre> *
 */
class ToastActivity : BaseBackActivity() {

    internal lateinit var toastView: View

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_toast
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_toast)

        findViewById<View>(R.id.btn_show_short_toast).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_long_toast).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_green_font).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_bg_color).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_bg_resource).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_span).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_custom_view).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_middle).setOnClickListener(this)
        findViewById<View>(R.id.btn_cancel_toast).setOnClickListener(this)
        toastView = findViewById(R.id.btn_cancel_toast)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        resetToast()
        when (view.id) {
            R.id.btn_show_short_toast -> Thread(Runnable { ToastUtils.showShort(R.string.toast_short) }).start()
            R.id.btn_show_long_toast -> Thread(Runnable { ToastUtils.showLong(R.string.toast_long) }).start()
            R.id.btn_show_green_font -> {
                ToastUtils.setMsgColor(Color.GREEN)
                ToastUtils.showLong(R.string.toast_green_font)
            }
            R.id.btn_show_bg_color -> {
                ToastUtils.setBgColor(ContextCompat.getColor(this, R.color.colorAccent))
                ToastUtils.showLong(R.string.toast_bg_color)
            }
            R.id.btn_show_bg_resource -> {
                ToastUtils.setBgResource(R.drawable.shape_round_rect)
                ToastUtils.showLong(R.string.toast_custom_bg)
            }
            R.id.btn_show_span -> ToastUtils.showLong(SpanUtils()
                    .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                    .appendSpace(32)
                    .append(getString(R.string.toast_span)).setFontSize(24, true)
                    .create()
            )
            R.id.btn_show_custom_view -> Thread(Runnable { CustomToast.showLong(R.string.toast_custom_view) }).start()
            R.id.btn_show_middle -> {
                ToastUtils.setGravity(Gravity.CENTER, 0, 0)
                ToastUtils.showLong(R.string.toast_middle)
            }
            R.id.btn_cancel_toast -> ToastUtils.cancel()
        }
    }

    override fun onDestroy() {
        resetToast()
        super.onDestroy()
    }

    private fun resetToast() {
        ToastUtils.setMsgColor(-0x1000001)
        ToastUtils.setBgColor(-0x1000001)
        ToastUtils.setBgResource(-1)
        ToastUtils.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, resources.getDimensionPixelSize(R.dimen.offset_64))
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ToastActivity::class.java)
            context.startActivity(starter)
        }
    }
}
