package com.blankj.androidutilcode.feature.core.sp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.data.DataManager

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/08
 * desc  : SP 工具类 Demo
</pre> *
 */
class SPActivity : BaseBackActivity() {

    private var tvAboutSp: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_sp
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        tvAboutSp = findViewById(R.id.tv_about_sp)
        findViewById<View>(R.id.btn_sp_put_string).setOnClickListener(this)
        findViewById<View>(R.id.btn_sp_put_int).setOnClickListener(this)
        findViewById<View>(R.id.btn_sp_put_long).setOnClickListener(this)
        findViewById<View>(R.id.btn_sp_put_float).setOnClickListener(this)
        findViewById<View>(R.id.btn_sp_put_boolean).setOnClickListener(this)
        findViewById<View>(R.id.btn_sp_clear).setOnClickListener(this)
    }

    override fun doBusiness() {
        updateAboutSp()
    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_sp_put_string -> DataManager.putString()
            R.id.btn_sp_put_int -> DataManager.putInt()
            R.id.btn_sp_put_long -> DataManager.putLong()
            R.id.btn_sp_put_float -> DataManager.putFloat()
            R.id.btn_sp_put_boolean -> DataManager.putBoolean()
            R.id.btn_sp_clear -> DataManager.clear()
        }
        updateAboutSp()
    }

    private fun updateAboutSp() {
        tvAboutSp!!.text = DataManager.sp2String()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, SPActivity::class.java)
            context.startActivity(starter)
        }
    }
}
