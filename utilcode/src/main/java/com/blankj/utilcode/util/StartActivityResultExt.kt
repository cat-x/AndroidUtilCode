package com.blankj.utilcode.util

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.content.Intent
import android.os.Bundle

/**
 * Created by Cat-x on 2018/2/8.
 * For QuickToolkit-master
 */

private class Result(val resultCode: Int, val data: Any?)

private var resultObjectData = HashMap<String, Result>()
/**
 * Activity请求的key
 */
private const val RESULT_KEY_CLASS_CODE = "result_key_class_code"
/**
 * 是否是Result扩展请求
 */
private const val RESULT_KEY_USE = "result_key_use"

fun Fragment.startActivityForObject(intent: Intent, requestCode: Int, callback: (requestCode: Int, resultCode: Int, data: Any?) -> Unit) {
    activity?.startActivityForObject(intent, requestCode, callback)
}

fun Activity.startActivityForObject(intent: Intent, requestCode: Int, callback: (requestCode: Int, resultCode: Int, data: Any?) -> Unit) {
    val requestKey = this.javaClass.canonicalName + "/" + requestCode
    application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {}

        override fun onActivityResumed(activity: Activity?) {
            if (this@startActivityForObject == activity/*activity != null && activity.javaClass == this@startResultActivity.javaClass*/) {//必须是同一个Activity，无需关心父类
                val result = resultObjectData[requestKey]
                callback(requestCode, result?.resultCode ?: Activity.RESULT_CANCELED, result?.data)
                releaseData()//当如果有人忘记设置setResult()，会存在影响没有取消监听ActivityLifecycleCallbacks的影响
            }
        }

        override fun onActivityStarted(activity: Activity?) {}

        override fun onActivityDestroyed(activity: Activity?) {
            /**
             * 当activity销毁时，需要判断是不是进行设置setData数据的Activity，因为可能有人会进行反复对同一个Activity调用
             */
            if (this@startActivityForObject == activity/*activity != null && activity.javaClass == this@startResultActivity.javaClass*/) {
                val data = resultObjectData[requestKey]
                if (data != null) {
                    releaseData()
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

        override fun onActivityStopped(activity: Activity?) {}

        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

        private fun releaseData() {
            resultObjectData.remove(requestKey)
            application.unregisterActivityLifecycleCallbacks(this)
        }

    })
    intent.putExtra(RESULT_KEY_CLASS_CODE, requestKey)
    intent.putExtra(RESULT_KEY_USE, true)
    this.startActivity(intent)
}

fun Fragment.setObjectResult(resultCode: Int = Activity.RESULT_OK, needFinish: Boolean = false) {
    activity?.setObjectResult(resultCode, null, needFinish)
}

fun Fragment.setObjectResult(resultCode: Int = Activity.RESULT_OK, data: Any?, needFinish: Boolean = false) {
    activity?.setObjectResult(resultCode, data, needFinish)
}

fun Activity.setObjectResult(resultCode: Int = Activity.RESULT_OK, needFinish: Boolean = false) {
    setObjectResult(resultCode, null, needFinish)
}

fun Activity.setObjectResult(resultCode: Int = Activity.RESULT_OK, data: Any?, needFinish: Boolean = false) {
    try {
        val intentPre = intent
        if (intentPre != null) {
            val isResultCall = intentPre.getBooleanExtra(RESULT_KEY_USE, false)
            if (isResultCall) {
                val requestKey = intentPre.getStringExtra(RESULT_KEY_CLASS_CODE)
                resultObjectData[requestKey] = Result(resultCode, data)
            } else {
                throw RuntimeException("You must be call setObjectResult before calling setIntent")
            }
        } else {
            throw RuntimeException("You must be call setObjectResult before calling setIntent")
        }
    } finally {
        if (needFinish) {
            finish()
        }
    }
}
