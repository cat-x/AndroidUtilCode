package com.blankj.androidutilcode.helper

import com.blankj.androidutilcode.Config
import com.blankj.subutil.util.ThreadPoolUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils

import java.io.IOException

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/07
 * desc  :
</pre> *
 */
object AssertHelper {

    fun releaseInstallApk(listener: OnReleasedListener?) {
        if (!FileUtils.isFileExists(Config.TEST_APK_PATH)) {
            val poolUtils = ThreadPoolUtils(ThreadPoolUtils.SingleThread, 1)
            poolUtils.execute(Runnable {
                try {
                    FileIOUtils.writeFileFromIS(
                            Config.TEST_APK_PATH,
                            Utils.app.assets.open("test_install"),
                            false
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                listener?.onReleased()
            })
        } else {
            listener?.onReleased()
            LogUtils.d("test apk existed.")
        }
    }

    interface OnReleasedListener {
        fun onReleased()
    }
}
