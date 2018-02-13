package com.blankj.androidutilcode.feature.core.reflect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.ReflectUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/29
 * desc  :
</pre> *
 */
class ReflectActivity : BaseBackActivity() {

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_reflect
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_reflect)

        val tvAboutReflect = findViewById<TextView>(R.id.tv_about_reflect)

        tvAboutReflect.text = SpanUtils()
                .appendLine("before reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("I1").get<Any>())
                .append("after reflect: " + ReflectUtils.reflect(TestPrivateStaticFinal::class.java).field("I1", 2).field("I1").get<Any>())
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ReflectActivity::class.java)
            context.startActivity(starter)
        }
    }
}
