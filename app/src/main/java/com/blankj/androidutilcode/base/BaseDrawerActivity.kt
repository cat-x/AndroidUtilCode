package com.blankj.androidutilcode.base

import android.content.Intent
import android.net.Uri
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.view.LayoutInflater
import android.widget.FrameLayout

import com.blankj.androidutilcode.Config
import com.blankj.androidutilcode.R


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/06/27
 * desc  : DrawerActivity 基类
</pre> *
 */
abstract class BaseDrawerActivity : BaseActivity() {

    protected lateinit var rootLayout: DrawerLayout
    protected lateinit var flActivityContainer: FrameLayout

    internal var mListener: NavigationView.OnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_git_hub -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Config.GITHUB)))
            R.id.action_blog -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Config.BLOG)))
        }
        false
    }

    override fun setBaseView(@LayoutRes layoutId: Int) {
        mContentView = LayoutInflater.from(this).inflate(R.layout.activity_drawer, null)
        setContentView(mContentView)
        rootLayout = findViewById(R.id.root_layout)
        flActivityContainer = findViewById(R.id.activity_container)
        flActivityContainer.addView(LayoutInflater.from(this).inflate(layoutId, flActivityContainer, false))
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(mListener)
    }
}
