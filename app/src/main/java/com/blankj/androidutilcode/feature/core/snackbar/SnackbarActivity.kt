package com.blankj.androidutilcode.feature.core.snackbar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.text.SpannableStringBuilder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.SnackbarUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/17
 * desc  : Snackbar 工具类 Demo
</pre> *
 */
class SnackbarActivity : BaseBackActivity() {

    internal lateinit var snackBarRootView: View

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_snackbar
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_snackbar)

        snackBarRootView = findViewById(android.R.id.content)
        findViewById<View>(R.id.btn_short_snackbar).setOnClickListener(this)
        findViewById<View>(R.id.btn_short_snackbar_with_action).setOnClickListener(this)
        findViewById<View>(R.id.btn_long_snackbar).setOnClickListener(this)
        findViewById<View>(R.id.btn_long_snackbar_with_action).setOnClickListener(this)
        findViewById<View>(R.id.btn_indefinite_snackbar).setOnClickListener(this)
        findViewById<View>(R.id.btn_indefinite_snackbar_with_action).setOnClickListener(this)
        findViewById<View>(R.id.btn_add_view).setOnClickListener(this)
        findViewById<View>(R.id.btn_add_view_with_action).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_success).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_warning).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_error).setOnClickListener(this)
        findViewById<View>(R.id.btn_dismiss_snackbar).setOnClickListener(this)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_short_snackbar -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .show()

            R.id.btn_short_snackbar_with_action -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_short))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, View.OnClickListener { ToastUtils.showShort(getString(R.string.snackbar_click)) })
                    .show()

            R.id.btn_long_snackbar -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .show()

            R.id.btn_long_snackbar_with_action -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_long))
                    .setMessageColor(Color.WHITE)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, View.OnClickListener { ToastUtils.showShort(getString(R.string.snackbar_click)) })
                    .show()

            R.id.btn_indefinite_snackbar -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .show()

            R.id.btn_indefinite_snackbar_with_action -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_indefinite))
                    .setMessageColor(Color.WHITE)
                    .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                    .setBgResource(R.drawable.shape_top_round_rect)
                    .setAction(getString(R.string.snackbar_click), Color.YELLOW, View.OnClickListener { ToastUtils.showShort(getString(R.string.snackbar_click)) })
                    .show()

            R.id.btn_add_view
            -> {
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show()
                SnackbarUtils.addView(R.layout.snackbar_custom, params)
            }

            R.id.btn_add_view_with_action -> {
                SnackbarUtils.with(snackBarRootView)
                        .setBgColor(Color.TRANSPARENT)
                        .setDuration(SnackbarUtils.LENGTH_INDEFINITE)
                        .show()
                val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                SnackbarUtils.addView(R.layout.snackbar_custom, params)
                val snackbarView = SnackbarUtils.view
                if (snackbarView != null) {
                    val tvSnackbarCustom = snackbarView.findViewById<TextView>(R.id.tv_snackbar_custom)
                    tvSnackbarCustom.text = "点我可消失"
                    snackbarView.setOnClickListener { SnackbarUtils.dismiss() }
                }
            }

            R.id.btn_show_success -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_success))
                    .showSuccess()

            R.id.btn_show_warning -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_warning))
                    .showWarning()

            R.id.btn_show_error -> SnackbarUtils.with(snackBarRootView)
                    .setMessage(getMsg(R.string.snackbar_error))
                    .showError()

            R.id.btn_dismiss_snackbar -> SnackbarUtils.dismiss()
        }
    }

    private fun getMsg(@StringRes resId: Int): SpannableStringBuilder {
        return SpanUtils()
                .appendImage(R.mipmap.ic_launcher, SpanUtils.ALIGN_CENTER)
                .appendSpace(32)
                .append(getString(resId)).setFontSize(24, true)
                .create()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, SnackbarActivity::class.java)
            context.startActivity(starter)
        }
    }
}
