package com.blankj.androidutilcode.feature.sub

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.feature.sub.brightness.BrightnessActivity
import com.blankj.androidutilcode.feature.sub.location.LocationActivity
import com.blankj.androidutilcode.feature.sub.pinyin.PinyinActivity

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : MainActivity
</pre> *
 */
class SubUtilActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_util_sub
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.sub_util)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    fun brightnessClick(view: View) {
        BrightnessActivity.start(this)
    }

    fun locationClick(view: View) {
        LocationActivity.start(this)
    }

    fun pinyinClick(view: View) {
        PinyinActivity.start(this)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, SubUtilActivity::class.java)
            context.startActivity(starter)
        }
    }
}
