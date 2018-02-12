package com.blankj.utilcode.util

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.AnimRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/23
 * desc  : Activity 相关工具类
</pre> *
 */
class ActivityUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 判断 Activity 是否存在
         *
         * @param packageName 包名
         * @param className   activity 全路径类名
         * @return `true`: 是<br></br>`false`: 否
         */
        fun isActivityExists(packageName: String,
                             className: String): Boolean {
            val intent = Intent()
            intent.setClassName(packageName, className)
            return !(Utils.app.getPackageManager().resolveActivity(intent, 0) == null ||
                    intent.resolveActivity(Utils.app.getPackageManager()) == null ||
                    Utils.app.getPackageManager().queryIntentActivities(intent, 0).size == 0)
        }

        /**
         * 启动 Activity
         *
         * @param clz Activity 类
         */
        fun startActivity(clz: Class<*>) {
            val context = activityOrApp
            startActivity(context, null, context.packageName, clz.name, null)
        }

        /**
         * 启动 Activity
         *
         * @param clz     Activity 类
         * @param options 跳转动画
         */
        fun startActivity(clz: Class<*>,
                          options: Bundle?) {
            val context = activityOrApp
            startActivity(context, null, context.packageName, clz.name, options)
        }

        /**
         * 启动 Activity
         *
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(clz: Class<*>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivity(context, null, context.packageName, clz.name,
                    getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param clz      Activity 类
         */
        fun startActivity(activity: Activity,
                          clz: Class<*>) {
            startActivity(activity, null, activity.packageName, clz.name, null)
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param clz      Activity 类
         * @param options  跳转动画
         */
        fun startActivity(activity: Activity,
                          clz: Class<*>,
                          options: Bundle?) {
            startActivity(activity, null, activity.packageName, clz.name, options)
        }

        /**
         * 启动 Activity
         *
         * @param activity       activity
         * @param clz            Activity 类
         * @param sharedElements 共享元素
         */
        fun startActivity(activity: Activity,
                          clz: Class<*>,
                          vararg sharedElements: View) {
            startActivity(activity, null, activity.packageName, clz.name,
                    getOptionsBundle(activity, sharedElements))
        }

        /**
         * 启动 Activity
         *
         * @param activity  activity
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(activity: Activity,
                          clz: Class<*>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {

            startActivity(activity, null, activity.packageName, clz.name,
                    getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param extras extras
         * @param clz    Activity 类
         */
        fun startActivity(extras: Bundle,
                          clz: Class<*>) {
            val context = activityOrApp
            startActivity(context, extras, context.packageName, clz.name, null)
        }

        /**
         * 启动 Activity
         *
         * @param extras  extras
         * @param clz     Activity 类
         * @param options 跳转动画
         */
        fun startActivity(extras: Bundle,
                          clz: Class<*>,
                          options: Bundle) {
            val context = activityOrApp
            startActivity(context, extras, context.packageName, clz.name, options)
        }

        /**
         * 启动 Activity
         *
         * @param extras    extras
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(extras: Bundle,
                          clz: Class<*>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivity(context, extras, context.packageName, clz.name,
                    getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param extras   extras
         * @param activity activity
         * @param clz      Activity 类
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<*>) {
            startActivity(activity, extras, activity.packageName, clz.name, null)
        }

        /**
         * 启动 Activity
         *
         * @param extras   extras
         * @param activity activity
         * @param clz      Activity 类
         * @param options  跳转动画
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<*>,
                          options: Bundle) {
            startActivity(activity, extras, activity.packageName, clz.name, options)
        }

        /**
         * 启动 Activity
         *
         * @param extras         extras
         * @param activity       activity
         * @param clz            Activity 类
         * @param sharedElements 共享元素
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<*>,
                          vararg sharedElements: View) {
            startActivity(activity, extras, activity.packageName, clz.name,
                    getOptionsBundle(activity, sharedElements))
        }

        /**
         * 启动 Activity
         *
         * @param extras    extras
         * @param activity  activity
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          clz: Class<*>,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, extras, activity.packageName, clz.name,
                    getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param pkg 包名
         * @param cls 全类名
         */
        fun startActivity(pkg: String,
                          cls: String) {
            startActivity(activityOrApp, null, pkg, cls, null)
        }

        /**
         * 启动 Activity
         *
         * @param pkg     包名
         * @param cls     全类名
         * @param options 动画
         */
        fun startActivity(pkg: String,
                          cls: String,
                          options: Bundle?) {
            startActivity(activityOrApp, null, pkg, cls, options)
        }

        /**
         * 启动 Activity
         *
         * @param pkg       包名
         * @param cls       全类名
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param pkg      包名
         * @param cls      全类名
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String) {
            startActivity(activity, null, pkg, cls, null)
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param pkg      包名
         * @param cls      全类名
         * @param options  动画
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          options: Bundle?) {
            startActivity(activity, null, pkg, cls, options)
        }

        /**
         * 启动 Activity
         *
         * @param activity       activity
         * @param pkg            包名
         * @param cls            全类名
         * @param sharedElements 共享元素
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          vararg sharedElements: View) {
            startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements))
        }

        /**
         * 启动 Activity
         *
         * @param activity  activity
         * @param pkg       包名
         * @param cls       全类名
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(activity: Activity,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param extras extras
         * @param pkg    包名
         * @param cls    全类名
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String) {
            startActivity(activityOrApp, extras, pkg, cls, null)
        }

        /**
         * 启动 Activity
         *
         * @param extras  extras
         * @param pkg     包名
         * @param cls     全类名
         * @param options 动画
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(activityOrApp, extras, pkg, cls, options)
        }

        /**
         * 启动 Activity
         *
         * @param extras    extras
         * @param pkg       包名
         * @param cls       全类名
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(extras: Bundle,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param extras   extras
         * @param pkg      包名
         * @param cls      全类名
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String) {
            startActivity(activity, extras, pkg, cls, null)
        }

        /**
         * 启动 Activity
         *
         * @param extras   extras
         * @param activity activity
         * @param pkg      包名
         * @param cls      全类名
         * @param options  动画
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          options: Bundle) {
            startActivity(activity, extras, pkg, cls, options)
        }

        /**
         * 启动 Activity
         *
         * @param extras         extras
         * @param activity       activity
         * @param pkg            包名
         * @param cls            全类名
         * @param sharedElements 共享元素
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          vararg sharedElements: View) {
            startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements))
        }

        /**
         * 启动 Activity
         *
         * @param extras    extras
         * @param pkg       包名
         * @param cls       全类名
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(extras: Bundle,
                          activity: Activity,
                          pkg: String,
                          cls: String,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param intent 意图
         */
        fun startActivity(intent: Intent) {
            startActivity(intent, activityOrApp, null)
        }

        /**
         * 启动 Activity
         *
         * @param intent  意图
         * @param options 跳转动画
         */
        fun startActivity(intent: Intent,
                          options: Bundle) {
            startActivity(intent, activityOrApp, options)
        }

        /**
         * 启动 Activity
         *
         * @param intent    意图
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(intent: Intent,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param intent   意图
         */
        fun startActivity(activity: Activity,
                          intent: Intent) {
            startActivity(intent, activity, null)
        }

        /**
         * 启动 Activity
         *
         * @param activity activity
         * @param intent   意图
         * @param options  跳转动画
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          options: Bundle?) {
            startActivity(intent, activity, options)
        }

        /**
         * 启动 Activity
         *
         * @param activity       activity
         * @param intent         意图
         * @param sharedElements 共享元素
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          vararg sharedElements: View) {
            startActivity(intent, activity, getOptionsBundle(activity, sharedElements))
        }

        /**
         * 启动 Activity
         *
         * @param activity  activity
         * @param intent    意图
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivity(activity: Activity,
                          intent: Intent,
                          @AnimRes enterAnim: Int,
                          @AnimRes exitAnim: Int) {
            startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动多个 Activity
         *
         * @param intents 意图
         */
        fun startActivities(intents: Array<Intent>) {
            startActivities(intents, activityOrApp, null)
        }

        /**
         * 启动多个 Activity
         *
         * @param intents 意图
         * @param options 跳转动画
         */
        fun startActivities(intents: Array<Intent>,
                            options: Bundle?) {
            startActivities(intents, activityOrApp, options)
        }

        /**
         * 启动多个 Activity
         *
         * @param intents   意图
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivities(intents: Array<Intent>,
                            @AnimRes enterAnim: Int,
                            @AnimRes exitAnim: Int) {
            val context = activityOrApp
            startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context is Activity) {
                context.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 启动多个 Activity
         *
         * @param activity activity
         * @param intents  意图
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>) {
            startActivities(intents, activity, null)
        }

        /**
         * 启动多个 Activity
         *
         * @param activity activity
         * @param intents  意图
         * @param options  跳转动画
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>,
                            options: Bundle?) {
            startActivities(intents, activity, options)
        }

        /**
         * 启动多个 Activity
         *
         * @param activity  activity
         * @param intents   意图
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun startActivities(activity: Activity,
                            intents: Array<Intent>,
                            @AnimRes enterAnim: Int,
                            @AnimRes exitAnim: Int) {
            startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim))
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 回到桌面
         */
        fun startHomeActivity() {
            val homeIntent = Intent(Intent.ACTION_MAIN)
            homeIntent.addCategory(Intent.CATEGORY_HOME)
            startActivity(homeIntent)
        }

        /**
         * 获取 Activity 栈链表
         *
         * @return Activity 栈链表
         */
        val activityList: List<Activity>
            get() = Utils.sActivityList

        /**
         * 获取启动项 Activity
         *
         * @return 启动项 Activity
         */
        val launcherActivity: String
            get() = getLauncherActivity(Utils.app.getPackageName())

        /**
         * 获取启动项 Activity
         *
         * @param packageName 包名
         * @return 启动项 Activity
         */
        fun getLauncherActivity(packageName: String): String {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val pm = Utils.app.getPackageManager()
            val info = pm.queryIntentActivities(intent, 0)
            for (aInfo in info) {
                if (aInfo.activityInfo.packageName == packageName) {
                    return aInfo.activityInfo.name
                }
            }
            return "no " + packageName
        }

        /**
         * 获取栈顶 Activity
         *
         * @return 栈顶 Activity
         */
        val topActivity: Activity?
            get() {
                if (Utils.sTopActivityWeakRef != null) {
                    val activity = Utils.sTopActivityWeakRef!!.get()
                    if (activity != null) {
                        return activity
                    }
                }
                val activities = Utils.sActivityList
                val size = activities.size
                return if (size > 0) activities[size - 1] else null
            }

        /**
         * 判断 Activity 是否存在栈中
         *
         * @param activity activity
         * @return `true`: 存在<br></br>`false`: 不存在
         */
        fun isActivityExistsInStack(activity: Activity): Boolean {
            val activities = Utils.sActivityList
            for (aActivity in activities) {
                if (aActivity == activity) {
                    return true
                }
            }
            return false
        }

        /**
         * 判断 Activity 是否存在栈中
         *
         * @param clz Activity 类
         * @return `true`: 存在<br></br>`false`: 不存在
         */
        fun isActivityExistsInStack(clz: Class<*>): Boolean {
            val activities = Utils.sActivityList
            for (aActivity in activities) {
                if (aActivity.javaClass == clz) {
                    return true
                }
            }
            return false
        }

        /**
         * 结束 Activity
         *
         * @param activity   activity
         * @param isLoadAnim 是否启动动画
         */
        @JvmOverloads
        fun finishActivity(activity: Activity, isLoadAnim: Boolean = false) {
            activity.finish()
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0)
            }
        }

        /**
         * 结束 Activity
         *
         * @param activity  activity
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun finishActivity(activity: Activity,
                           @AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int) {
            activity.finish()
            activity.overridePendingTransition(enterAnim, exitAnim)
        }

        /**
         * 结束 Activity
         *
         * @param clz        Activity 类
         * @param isLoadAnim 是否启动动画
         */
        @JvmOverloads
        fun finishActivity(clz: Class<*>, isLoadAnim: Boolean = false) {
            val activities = Utils.sActivityList
            for (activity in activities) {
                if (activity.javaClass == clz) {
                    activity.finish()
                    if (!isLoadAnim) {
                        activity.overridePendingTransition(0, 0)
                    }
                }
            }
        }

        /**
         * 结束 Activity
         *
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun finishActivity(clz: Class<*>,
                           @AnimRes enterAnim: Int,
                           @AnimRes exitAnim: Int) {
            val activities = Utils.sActivityList
            for (activity in activities) {
                if (activity.javaClass == clz) {
                    activity.finish()
                    activity.overridePendingTransition(enterAnim, exitAnim)
                }
            }
        }

        /**
         * 结束到指定 Activity
         *
         * @param activity      activity
         * @param isIncludeSelf 是否结束该 activity 自己
         * @param isLoadAnim    是否启动动画
         */
        @JvmOverloads
        fun finishToActivity(activity: Activity,
                             isIncludeSelf: Boolean,
                             isLoadAnim: Boolean = false): Boolean {
            val activities = Utils.sActivityList
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity == activity) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, isLoadAnim)
                    }
                    return true
                }
                finishActivity(aActivity, isLoadAnim)
            }
            return false
        }

        /**
         * 结束到指定 Activity
         *
         * @param activity      activity
         * @param isIncludeSelf 是否结束该 activity 自己
         * @param enterAnim     入场动画
         * @param exitAnim      出场动画
         */
        fun finishToActivity(activity: Activity,
                             isIncludeSelf: Boolean,
                             @AnimRes enterAnim: Int,
                             @AnimRes exitAnim: Int): Boolean {
            val activities = Utils.sActivityList
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity == activity) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, enterAnim, exitAnim)
                    }
                    return true
                }
                finishActivity(aActivity, enterAnim, exitAnim)
            }
            return false
        }

        /**
         * 结束到指定 Activity
         *
         * @param clz           Activity 类
         * @param isIncludeSelf 是否结束该 activity 自己
         * @param isLoadAnim    是否启动动画
         */
        @JvmOverloads
        fun finishToActivity(clz: Class<*>,
                             isIncludeSelf: Boolean,
                             isLoadAnim: Boolean = false): Boolean {
            val activities = Utils.sActivityList
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity.javaClass == clz) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, isLoadAnim)
                    }
                    return true
                }
                finishActivity(aActivity, isLoadAnim)
            }
            return false
        }

        /**
         * 结束到指定 Activity
         *
         * @param clz           Activity 类
         * @param isIncludeSelf 是否结束该 activity 自己
         * @param enterAnim     入场动画
         * @param exitAnim      出场动画
         */
        fun finishToActivity(clz: Class<*>,
                             isIncludeSelf: Boolean,
                             @AnimRes enterAnim: Int,
                             @AnimRes exitAnim: Int): Boolean {
            val activities = Utils.sActivityList
            for (i in activities.indices.reversed()) {
                val aActivity = activities[i]
                if (aActivity.javaClass == clz) {
                    if (isIncludeSelf) {
                        finishActivity(aActivity, enterAnim, exitAnim)
                    }
                    return true
                }
                finishActivity(aActivity, enterAnim, exitAnim)
            }
            return false
        }


        /**
         * 结束所有其他类型的 Activity
         *
         * @param clz        Activity 类
         * @param isLoadAnim 是否启动动画
         */
        @JvmOverloads
        fun finishOtherActivities(clz: Class<*>,
                                  isLoadAnim: Boolean = false) {
            val activities = Utils.sActivityList
            var flag = false
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (activity.javaClass == clz) {
                    if (flag) {
                        finishActivity(activity, isLoadAnim)
                    } else {
                        flag = true
                    }
                } else {
                    finishActivity(activity, isLoadAnim)
                }
            }
        }

        /**
         * 结束所有其他类型的 Activity
         *
         * @param clz       Activity 类
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun finishOtherActivities(clz: Class<*>,
                                  @AnimRes enterAnim: Int,
                                  @AnimRes exitAnim: Int) {
            val activities = Utils.sActivityList
            var flag = false
            for (i in activities.indices.reversed()) {
                val activity = activities[i]
                if (activity.javaClass == clz) {
                    if (flag) {
                        finishActivity(activity, enterAnim, exitAnim)
                    } else {
                        flag = true
                    }
                } else {
                    finishActivity(activity, enterAnim, exitAnim)
                }
            }
        }

        /**
         * 结束所有 Activity
         *
         * @param isLoadAnim 是否启动动画
         */
        @JvmOverloads
        fun finishAllActivities(isLoadAnim: Boolean = false) {
            val activityList = Utils.sActivityList
            for (i in activityList.indices.reversed()) {// 从栈顶开始移除
                val activity = activityList[i]
                activity.finish()// 在 onActivityDestroyed 发生 remove
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0)
                }
            }
        }

        /**
         * 结束所有 Activity
         *
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun finishAllActivities(@AnimRes enterAnim: Int,
                                @AnimRes exitAnim: Int) {
            val activityList = Utils.sActivityList
            for (i in activityList.indices.reversed()) {// 从栈顶开始移除
                val activity = activityList[i]
                activity.finish()// 在 onActivityDestroyed 发生 remove
                activity.overridePendingTransition(enterAnim, exitAnim)
            }
        }

        /**
         * 结束除最新之外的所有 Activity
         *
         * @param isLoadAnim 是否启动动画
         */
        @JvmOverloads
        fun finishAllActivitiesExceptNewest(isLoadAnim: Boolean = false) {
            val activities = Utils.sActivityList
            val flag = false
            for (i in activities.size - 2 downTo 0) {
                finishActivity(activities[i], isLoadAnim)
            }
        }

        /**
         * 结束除最新之外的所有 Activity
         *
         * @param enterAnim 入场动画
         * @param exitAnim  出场动画
         */
        fun finishAllActivitiesExceptNewest(@AnimRes enterAnim: Int,
                                            @AnimRes exitAnim: Int) {
            val activities = Utils.sActivityList
            val flag = false
            for (i in activities.size - 2 downTo 0) {
                finishActivity(activities[i], enterAnim, exitAnim)
            }
        }

        /**
         * 获取 Activity 图标
         *
         * @param clz Activity 类
         * @return Activity 图标
         */
        fun getActivityIcon(clz: Class<*>): Drawable? {
            return getActivityIcon(ComponentName(Utils.app, clz))
        }

        /**
         * 获取 Activity 图标
         *
         * @param activityName activityName
         * @return Activity 图标
         */
        fun getActivityIcon(activityName: ComponentName): Drawable? {
            val pm = Utils.app.getPackageManager()
            try {
                return pm.getActivityIcon(activityName)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }

        }

        /**
         * 获取 Activity Logo
         *
         * @param clz Activity 类
         * @return Activity Logo
         */
        fun getActivityLogo(clz: Class<*>): Drawable? {
            return getActivityLogo(ComponentName(Utils.app, clz))
        }

        /**
         * 获取 Activity Logo
         *
         * @param activityName activityName
         * @return Activity Logo
         */
        fun getActivityLogo(activityName: ComponentName): Drawable? {
            val pm = Utils.app.getPackageManager()
            try {
                return pm.getActivityLogo(activityName)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                return null
            }

        }


        private val activityOrApp: Context
            get() {
                val topActivity = topActivity
                return topActivity ?: Utils.app
            }

        private fun startActivity(context: Context,
                                  extras: Bundle?,
                                  pkg: String,
                                  cls: String,
                                  options: Bundle?) {
            val intent = Intent(Intent.ACTION_VIEW)
            if (extras != null) intent.putExtras(extras)
            intent.component = ComponentName(pkg, cls)
            startActivity(intent, context, options)
        }

        private fun startActivity(intent: Intent,
                                  context: Context,
                                  options: Bundle?) {
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(intent, options)
            } else {
                context.startActivity(intent)
            }
        }

        private fun startActivities(intents: Array<Intent>,
                                    context: Context,
                                    options: Bundle?) {
            if (context !is Activity) {
                for (intent in intents) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivities(intents, options)
            } else {
                context.startActivities(intents)
            }
        }

        private fun getOptionsBundle(context: Context,
                                     enterAnim: Int,
                                     exitAnim: Int): Bundle {
            return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle()
        }

        private fun getOptionsBundle(activity: Activity,
                                     sharedElements: Array<out View>): Bundle {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val len = sharedElements.size
                val pairs = arrayOfNulls<Pair<View, String>>(len)
                for (i in 0 until len) {
                    pairs[i] = Pair.create(sharedElements[i], sharedElements[i].transitionName)
                }
                return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs).toBundle()
            }
            return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, null as View?, null as String?).toBundle()
        }
    }
}
/**
 * 结束 Activity
 *
 * @param activity activity
 */
/**
 * 结束 Activity
 *
 * @param clz Activity 类
 */
/**
 * 结束到指定 Activity
 *
 * @param activity      activity
 * @param isIncludeSelf 是否结束该 activity 自己
 */
/**
 * 结束到指定 Activity
 *
 * @param clz           Activity 类
 * @param isIncludeSelf 是否结束该 activity 自己
 */
/**
 * 结束所有其他类型的 Activity
 *
 * @param clz Activity 类
 */
/**
 * 结束所有 Activity
 */
/**
 * 结束除最新之外的所有 Activity
 */
