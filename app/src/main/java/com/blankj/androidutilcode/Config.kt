package com.blankj.androidutilcode

import android.os.Environment
import com.blankj.utilcode.util.Utils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/10
 * desc  : 配置常量
</pre> *
 */
object Config {

    val FILE_SEP = System.getProperty("file.separator")
    val LINE_SEP = System.getProperty("line.separator")
    val PKG = "com.blankj.androidutilcode"
    val TEST_PKG = "com.blankj.testinstall"
    val GITHUB = "https://github.com/Blankj/AndroidUtilCode"
    val BLOG = "http://www.jianshu.com/u/46702d5c6978"
    val CACHE_PATH: String
    val TEST_APK_PATH: String

    init {
        val cacheDir = Utils.app.externalCacheDir
        if (cacheDir != null) {
            CACHE_PATH = cacheDir.absolutePath
        } else {
            CACHE_PATH = Environment.getExternalStorageDirectory().absolutePath
        }
        TEST_APK_PATH = CACHE_PATH + FILE_SEP + "test_install.apk"

    }
}
