package com.blankj.androidutilcode.feature.core.process

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.ProcessUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/13
 * desc  : Process 工具类 Demo
</pre> *
 */
class ProcessActivity : BaseBackActivity() {

    private var tvAboutProcess: TextView? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_process
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_process)

        findViewById<View>(R.id.btn_kill_all_background_processes).setOnClickListener(this)
        tvAboutProcess = findViewById(R.id.tv_about_process)
        val set = ProcessUtils.allBackgroundProcesses
        tvAboutProcess!!.text = SpanUtils()
                .appendLine("getForegroundProcessName: " + ProcessUtils.foregroundProcessName!!)
                .appendLine("getAllBackgroundProcesses: " + getSetItems(set))
                .append("size: " + set.size)
                .create()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_kill_all_background_processes -> {
                val set = ProcessUtils.allBackgroundProcesses
                val set1 = ProcessUtils.killAllBackgroundProcesses()
                tvAboutProcess!!.text = SpanUtils()
                        .appendLine("getForegroundProcessName: " + ProcessUtils.foregroundProcessName!!)
                        .appendLine("getAllBackgroundProcesses: " + getSetItems(set))
                        .appendLine("size: " + set.size)
                        .appendLine("killAllBackgroundProcesses: " + getSetItems(set1))
                        .append("size: " + set1.size)
                        .create()
            }
        }
    }

    private fun getSetItems(set: Set<String>): String {
        val iterator = set.iterator()
        val sb = StringBuilder()
        while (iterator.hasNext()) {
            sb.append(iterator.next()).append("\n")
        }
        return sb.toString()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, ProcessActivity::class.java)
            context.startActivity(starter)
        }
    }
}
