package com.blankj.androidutilcode.feature.core.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseActivity
import com.blankj.utilcode.util.FragmentUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/01
 * desc  : Fragment 工具类 Demo
</pre> *
 */
class FragmentActivity : BaseActivity() {

    private val itemIds = intArrayOf(R.id.navigation_fragment_zero, R.id.navigation_fragment_one, R.id.navigation_fragment_two)

    private var navigation: BottomNavigationView? = null
    private val mFragments = arrayOfNulls<Fragment>(3)
    private var curIndex: Int = 0


    //    @Override
    //    public void onBackPressed() {
    ////        if (!FragmentUtils.dispatchBackPress(getSupportFragmentManager())) {
    ////            super.onBackPressed();
    ////        }
    //    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_fragment_zero -> {
                showCurrentFragment(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fragment_one -> {
                showCurrentFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fragment_two -> {
                showCurrentFragment(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_fragment
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        if (savedInstanceState != null) {
            curIndex = savedInstanceState.getInt("curIndex")
        }

        navigation = findViewById(R.id.navigation_fragment)
        navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        mFragments[0] = Root0Fragment.newInstance()
        mFragments[1] = Root1Fragment.newInstance()
        mFragments[2] = Root2Fragment.newInstance()
        FragmentUtils.add(supportFragmentManager, mFragments as Array<Fragment>, R.id.fragment_container, curIndex)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    private fun showCurrentFragment(index: Int) {
        curIndex = index
        FragmentUtils.showHide(curIndex, *mFragments as Array<out Fragment>)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("curIndex", curIndex)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, FragmentActivity::class.java)
            context.startActivity(starter)
        }
    }
}
