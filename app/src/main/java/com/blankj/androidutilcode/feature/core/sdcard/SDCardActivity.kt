package com.blankj.androidutilcode.feature.core.sdcard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.SDCardUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : SDCard 工具类 Demo
</pre> *
 */
class SDCardActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_sdcard
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_sdcard)

        val tvAboutSdcard = findViewById<TextView>(R.id.tv_about_sdcard)
        tvAboutSdcard.text = SpanUtils()
                .appendLine("isSDCardEnable: " + SDCardUtils.isSDCardEnable)
                .appendLine("getSDCardPaths: " + SDCardUtils.sdCardPaths)
                .appendLine("getInnerSDCardPaths: " + SDCardUtils.getSDCardPaths(true))
                .appendLine("getOuterSDCardPaths: " + SDCardUtils.getSDCardPaths(false))
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, SDCardActivity::class.java)
            context.startActivity(starter)
        }
    }
}
