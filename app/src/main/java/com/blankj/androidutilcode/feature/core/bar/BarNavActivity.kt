package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.UtilsApp
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
class BarNavActivity : BaseBackActivity() {

    private var tvAboutNav: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_nav
    }


    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_bar)

        tvAboutNav = findViewById(R.id.tv_about_nav)
        findViewById<View>(R.id.btn_show_nav).setOnClickListener(this)
        findViewById<View>(R.id.btn_hide_nav).setOnClickListener(this)
        findViewById<View>(R.id.btn_immersive_nav).setOnClickListener(this)
        updateAboutNav()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_show_nav -> {
                BarUtils.setNavBarVisibility(this, true)
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(UtilsApp.instance, R.color.colorPrimary), 0)
                BarUtils.addMarginTopEqualStatusBarHeight(rootLayout)
            }
            R.id.btn_hide_nav -> BarUtils.setNavBarVisibility(this, false)
            R.id.btn_immersive_nav -> BarUtils.setNavBarImmersive(this)
        }
        updateAboutNav()
    }

    private fun updateAboutNav() {
        tvAboutNav!!.text = SpanUtils()
                .appendLine("navHeight: " + BarUtils.navBarHeight)
                .append("isNavBarVisible: " + BarUtils.isNavBarVisible(this))
                .create()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        BarUtils.setNavBarVisibility(this, false)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarNavActivity::class.java)
            context.startActivity(starter)
        }
    }
}
