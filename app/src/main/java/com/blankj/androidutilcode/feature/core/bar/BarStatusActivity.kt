package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Activity 工具类 Demo
</pre> *
 */
class BarStatusActivity : BaseBackActivity() {

    private var tvAboutStatus: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status
    }


    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_bar)

        tvAboutStatus = findViewById(R.id.tv_about_status)
        findViewById<View>(R.id.btn_show_status).setOnClickListener(this)
        findViewById<View>(R.id.btn_hide_status).setOnClickListener(this)
        findViewById<View>(R.id.btn_light_mode).setOnClickListener(this)
        findViewById<View>(R.id.btn_dark_mode).setOnClickListener(this)
        updateAboutStatus()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_show_status -> BarUtils.setStatusBarVisibility(this, true)
            R.id.btn_hide_status -> BarUtils.setStatusBarVisibility(this, false)
            R.id.btn_light_mode -> BarUtils.setStatusBarLightMode(this, true)
            R.id.btn_dark_mode -> BarUtils.setStatusBarLightMode(this, false)
        }
        updateAboutStatus()
    }

    private fun updateAboutStatus() {
        tvAboutStatus!!.text = SpanUtils()
                .appendLine("statusHeight: " + BarUtils.statusBarHeight)
                .append("isStatusVisible: " + BarUtils.isStatusBarVisible(this))
                .create()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarStatusActivity::class.java)
            context.startActivity(starter)
        }
    }
}
