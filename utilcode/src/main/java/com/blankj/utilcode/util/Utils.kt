package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.*


/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils about initialization.
 * </pre>
 */
@SuppressLint("StaticFieldLeak")
object Utils {

    private var sApplication: Application? = null

    internal var sTopActivityWeakRef: WeakReference<Activity>? = null
    internal var sActivityList: MutableList<Activity> = LinkedList()

    private val mCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
            if (activity != null) {
                sActivityList.add(activity)
                setTopActivityWeakRef(activity)
            }

        }

        override fun onActivityStarted(activity: Activity?) {
            if (activity != null) {
                setTopActivityWeakRef(activity)
            }
        }

        override fun onActivityResumed(activity: Activity?) {
            if (activity != null) {
                setTopActivityWeakRef(activity)
            }
        }

        override fun onActivityPaused(activity: Activity?) {

        }

        override fun onActivityStopped(activity: Activity?) {

        }

        override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {

        }

        override fun onActivityDestroyed(activity: Activity?) {
            sActivityList.remove(activity)
        }
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    fun init(context: Context) {
        Utils.sApplication = context.applicationContext as Application
        Utils.sApplication!!.registerActivityLifecycleCallbacks(mCallbacks)
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    val app: Application
        get() {
            if (sApplication != null) return sApplication!!
            throw NullPointerException("u should init first")
        }

    private fun setTopActivityWeakRef(activity: Activity) {
        if (activity.javaClass == PermissionUtils.PermissionActivity::class.java) return
        if (sTopActivityWeakRef == null || activity != sTopActivityWeakRef!!.get()) {
            sTopActivityWeakRef = WeakReference(activity)
        }
    }
}

