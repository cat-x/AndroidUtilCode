package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.BarUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Activity 工具类 Demo
</pre> *
 */
class BarNotificationActivity : BaseBackActivity() {

    private val mHandler = Handler()

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_notification
    }


    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_bar)

        findViewById<View>(R.id.btn_show_notification).setOnClickListener(this)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_show_notification -> {
                BarUtils.setNotificationBarVisibility(true)
                mHandler.postDelayed({ BarUtils.setNotificationBarVisibility(false) }, 2000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarNotificationActivity::class.java)
            context.startActivity(starter)
        }
    }
}
