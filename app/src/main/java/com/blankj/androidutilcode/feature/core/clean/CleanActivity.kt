package com.blankj.androidutilcode.feature.core.clean

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.View

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.utilcode.util.CleanUtils
import com.blankj.utilcode.util.SnackbarUtils

import java.io.File

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : Clean 工具类 Demo
</pre> *
 */
class CleanActivity : BaseBackActivity() {

    private var snackBarRootView: View? = null
    private var internalCachePath: String? = null
    private var internalFilesPath: String? = null
    private var internalDbs: String? = null
    private var internalSp: String? = null
    private var externalCache: String? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_clean
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_clean)

        snackBarRootView = findViewById(android.R.id.content)
        findViewById<View>(R.id.btn_clean_internal_cache).setOnClickListener(this)
        findViewById<View>(R.id.btn_clean_internal_files).setOnClickListener(this)
        findViewById<View>(R.id.btn_clean_internal_databases).setOnClickListener(this)
        findViewById<View>(R.id.btn_clean_internal_sp).setOnClickListener(this)
        findViewById<View>(R.id.btn_clean_external_cache).setOnClickListener(this)

        internalCachePath = cacheDir.path
        internalFilesPath = filesDir.path
        internalDbs = filesDir.parent + File.separator + "databases"
        internalSp = filesDir.parent + File.separator + "shared_prefs"

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            externalCache = externalCacheDir!!.absolutePath
        }
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_clean_internal_cache -> showSnackbar(CleanUtils.cleanInternalCache(), internalCachePath)
            R.id.btn_clean_internal_files -> showSnackbar(CleanUtils.cleanInternalFiles(), internalFilesPath)
            R.id.btn_clean_internal_databases -> showSnackbar(CleanUtils.cleanInternalDbs(), internalDbs)
            R.id.btn_clean_internal_sp -> showSnackbar(CleanUtils.cleanInternalSp(), internalSp)
            R.id.btn_clean_external_cache -> showSnackbar(CleanUtils.cleanExternalCache(), externalCache)
        }
    }

    private fun showSnackbar(isSuccess: Boolean, path: String?) {
        if (isSuccess) {
            SnackbarUtils.with(snackBarRootView!!)
                    .setMessage("clean \"$path\" dir success")
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .showSuccess()
        } else {
            SnackbarUtils.with(snackBarRootView!!)
                    .setMessage("clean \"$path\" dir failed")
                    .setDuration(SnackbarUtils.LENGTH_LONG)
                    .showError()
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, CleanActivity::class.java)
            context.startActivity(starter)
        }
    }
}
