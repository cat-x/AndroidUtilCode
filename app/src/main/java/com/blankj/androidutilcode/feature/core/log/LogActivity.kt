package com.blankj.androidutilcode.feature.core.log

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.UtilsApp
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.LogUtils


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/22
 * desc  : Log 工具类 Demo
</pre> *
 */
class LogActivity : BaseBackActivity() {

    private var tvAboutLog: TextView? = null

    private val mConfig = LogUtils.getConfig()

    private var dir: String? = ""
    private var globalTag = ""
    private var log = true
    private var console = true
    private var head = true
    private var file = false
    private var border = true
    private var consoleFilter = LogUtils.V
    private var fileFilter = LogUtils.V

    private val mRunnable = Runnable {
        LogUtils.v("verbose")
        LogUtils.d("debug")
        LogUtils.i("info")
        LogUtils.w("warn")
        LogUtils.e("error")
        LogUtils.a("assert")
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_log
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.setTitle(getString(R.string.demo_log))

        tvAboutLog = findViewById(R.id.tv_about_log)
        findViewById<View>(R.id.btn_toggle_log).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_console).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_tag).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_head).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_border).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_file).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_dir).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_conole_filter).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_file_filter).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_no_tag).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_with_tag).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_in_new_thread).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_null).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_many_params).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_long).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_file).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_json).setOnClickListener(this)
        findViewById<View>(R.id.btn_log_xml).setOnClickListener(this)
        updateConfig(0)
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_toggle_log -> updateConfig(UPDATE_LOG)
            R.id.btn_toggle_console -> updateConfig(UPDATE_CONSOLE)
            R.id.btn_toggle_tag -> updateConfig(UPDATE_TAG)
            R.id.btn_toggle_head -> updateConfig(UPDATE_HEAD)
            R.id.btn_toggle_file -> updateConfig(UPDATE_FILE)
            R.id.btn_toggle_dir -> updateConfig(UPDATE_DIR)
            R.id.btn_toggle_border -> updateConfig(UPDATE_BORDER)
            R.id.btn_toggle_conole_filter -> updateConfig(UPDATE_CONSOLE_FILTER)
            R.id.btn_toggle_file_filter -> updateConfig(UPDATE_FILE_FILTER)
            R.id.btn_log_no_tag -> {
                LogUtils.v("verbose")
                LogUtils.d("debug")
                LogUtils.i("info")
                LogUtils.w("warn")
                LogUtils.e("error")
                LogUtils.a("assert")
            }
            R.id.btn_log_with_tag -> {
                LogUtils.vTag("customTag", "verbose")
                LogUtils.dTag("customTag", "debug")
                LogUtils.iTag("customTag", "info")
                LogUtils.wTag("customTag", "warn")
                LogUtils.eTag("customTag", "error")
                LogUtils.aTag("customTag", "assert")
            }
            R.id.btn_log_in_new_thread -> {
                val thread = Thread(mRunnable)
                thread.start()
            }
            R.id.btn_log_null -> {
                LogUtils.v((null as Any?)!!)
                LogUtils.d((null as Any?)!!)
                LogUtils.i((null as Any?)!!)
                LogUtils.w((null as Any?)!!)
                LogUtils.e((null as Any?)!!)
                LogUtils.a((null as Any?)!!)
            }
            R.id.btn_log_many_params -> {
                LogUtils.v("verbose0", "verbose1")
                LogUtils.vTag("customTag", "verbose0", "verbose1")
                LogUtils.d("debug0", "debug1")
                LogUtils.dTag("customTag", "debug0", "debug1")
                LogUtils.i("info0", "info1")
                LogUtils.iTag("customTag", "info0", "info1")
                LogUtils.w("warn0", "warn1")
                LogUtils.wTag("customTag", "warn0", "warn1")
                LogUtils.e("error0", "error1")
                LogUtils.eTag("customTag", "error0", "error1")
                LogUtils.a("assert0", "assert1")
                LogUtils.aTag("customTag", "assert0", "assert1")
            }
            R.id.btn_log_long -> LogUtils.d(longStr)
            R.id.btn_log_file -> for (i in 0..99) {
                LogUtils.file("test0 log to file")
                LogUtils.file(LogUtils.I, "test0 log to file")
            }
            R.id.btn_log_json -> {
                val json = "{\"tools\": [{ \"name\":\"css format\" , \"site\":\"http://tools.w3cschool.cn/code/css\" },{ \"name\":\"json format\" , \"site\":\"http://tools.w3cschool.cn/code/json\" },{ \"name\":\"pwd check\" , \"site\":\"http://tools.w3cschool.cn/password/my_password_safe\" }]}"
                LogUtils.json(json)
                LogUtils.json(LogUtils.I, json)
            }
            R.id.btn_log_xml -> {
                val xml = "<books><book><author>Jack Herrington</author><title>PHP Hacks</title><publisher>O'Reilly</publisher></book><book><author>Jack Herrington</author><title>Podcasting Hacks</title><publisher>O'Reilly</publisher></book></books>"
                LogUtils.xml(xml)
                LogUtils.xml(LogUtils.I, xml)
            }
        }
    }

    private fun updateConfig(args: Int) {
        when (args) {
            UPDATE_LOG -> log = !log
            UPDATE_CONSOLE -> console = !console
            UPDATE_TAG -> globalTag = if (globalTag == TAG) "" else TAG
            UPDATE_HEAD -> head = !head
            UPDATE_FILE -> file = !file
            UPDATE_DIR -> if (getDir().contains("test")) {
                dir = null
            } else {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
                    dir = Environment.getExternalStorageDirectory().path + System.getProperty("file.separator") + "test"
                }
            }
            UPDATE_BORDER -> border = !border
            UPDATE_CONSOLE_FILTER -> consoleFilter = if (consoleFilter == LogUtils.V) LogUtils.W else LogUtils.V
            UPDATE_FILE_FILTER -> fileFilter = if (fileFilter == LogUtils.V) LogUtils.I else LogUtils.V
        }
        mConfig.setLogSwitch(log)
                .setConsoleSwitch(console)
                .setGlobalTag(globalTag)
                .setLogHeadSwitch(head)
                .setLog2FileSwitch(file)
                .setDir(dir!!)
                .setBorderSwitch(border)
                .setConsoleFilter(consoleFilter)
                .setFileFilter(fileFilter)
        tvAboutLog!!.setText(mConfig.toString())
    }

    private fun getDir(): String {
        return mConfig.toString().split(System.getProperty("line.separator").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[5].substring(5)
    }

    override fun onDestroy() {
        UtilsApp.instance!!.initLog()
        super.onDestroy()
    }

    companion object {

        private val TAG = "CMJ"

        private val UPDATE_LOG = 0x01
        private val UPDATE_CONSOLE = 0x01 shl 1
        private val UPDATE_TAG = 0x01 shl 2
        private val UPDATE_HEAD = 0x01 shl 3
        private val UPDATE_FILE = 0x01 shl 4
        private val UPDATE_DIR = 0x01 shl 5
        private val UPDATE_BORDER = 0x01 shl 6
        private val UPDATE_CONSOLE_FILTER = 0x01 shl 7
        private val UPDATE_FILE_FILTER = 0x01 shl 8

        private val longStr: String

        init {
            val sb = StringBuilder()
            sb.append("len = 10400\ncontent = \"")
            for (i in 0..799) {
                sb.append("Hello world. ")
            }
            sb.append("\"")
            longStr = sb.toString()
        }

        fun start(context: Context) {
            val starter = Intent(context, LogActivity::class.java)
            context.startActivity(starter)
        }
    }
}
