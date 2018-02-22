package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseActivity
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : Bar 工具类 Demo
</pre> *
 */
class BarStatusFragmentActivity : BaseActivity() {

    private val itemIds = intArrayOf(R.id.navigation_color, R.id.navigation_alpha, R.id.navigation_image_view)

    private var mVpStatusBar: ViewPager? = null
    private var navigation: BottomNavigationView? = null
    private val mFragmentList = ArrayList<Fragment>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_color -> {
                mVpStatusBar!!.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alpha -> {
                mVpStatusBar!!.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_image_view -> {
                mVpStatusBar!!.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_fragment
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        mVpStatusBar = findViewById(R.id.vp_status_bar)
        navigation = findViewById(R.id.navigation_status_bar)

        mFragmentList.add(BarStatusColorFragment.newInstance())
        mFragmentList.add(BarStatusAlphaFragment.newInstance())
        mFragmentList.add(BarStatusImageViewFragment.newInstance())


        mVpStatusBar!!.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragmentList[position]
            }

            override fun getCount(): Int {
                return mFragmentList.size
            }
        }

        mVpStatusBar!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                navigation!!.selectedItemId = itemIds[position]
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarStatusFragmentActivity::class.java)
            context.startActivity(starter)
        }
    }
}
