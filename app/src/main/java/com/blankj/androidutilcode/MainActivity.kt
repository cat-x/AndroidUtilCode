package com.blankj.androidutilcode

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View

import com.blankj.androidutilcode.base.BaseDrawerActivity
import com.blankj.androidutilcode.feature.core.CoreUtilActivity
import com.blankj.androidutilcode.feature.sub.SubUtilActivity
import com.blankj.utilcode.util.BarUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
</pre> *
 */
class MainActivity : BaseDrawerActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val fakeStatusBar = findViewById<View>(R.id.fake_status_bar)
        val ctl = findViewById<CollapsingToolbarLayout>(R.id.ctl)
        ctl.setExpandedTitleColor(Color.parseColor("#00FFFFFF"))
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,
                rootLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        rootLayout.addDrawerListener(toggle)
        toggle.syncState()

        BarUtils.setStatusBarAlpha4Drawer(this, rootLayout, fakeStatusBar, 0, false)
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    fun coreUtilClick(view: View) {
        CoreUtilActivity.start(this)
    }

    fun subUtilClick(view: View) {
        SubUtilActivity.start(this)
    }
}
