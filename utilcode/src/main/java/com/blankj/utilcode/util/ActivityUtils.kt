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
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : Utils about activity.
 * </pre>
 */
object ActivityUtils {


    /**
     * Return whether the activity exists.
     *
     * @param packageName The name of the package.
     * @param className The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isActivityExists(packageName: String, className: String): Boolean {
        val intent = Intent()
        intent.setClassName(packageName, className)
        return !(Utils.app.packageManager.resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.app.packageManager) == null ||
                Utils.app.packageManager.queryIntentActivities(intent, 0).size == 0)
    }

    /**
     * Start the activity.
     *
     * @param clz The activity class.
     */
    fun startActivity(clz: Class<*>) {
        val context = activityOrApp
        startActivity(context, null, context.packageName, clz.name, null)
    }

    /**
     * Start the activity.
     *
     * @param clz     The activity class.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivity(clz: Class<*>,
                      options: Bundle?) {
        val context = activityOrApp
        startActivity(context, null, context.packageName, clz.name, options)
    }

    /**
     * Start the activity.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param activity activity
     * @param clz      The activity class.
     */
    fun startActivity(activity: Activity,
                      clz: Class<*>) {
        startActivity(activity, null, activity.packageName, clz.name, null)
    }

    /**
     * Start the activity.
     *
     * @param activity activity
     * @param clz      The activity class.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivity(activity: Activity,
                      clz: Class<*>,
                      options: Bundle?) {
        startActivity(activity, null, activity.packageName, clz.name, options)
    }

    /**
     * Start the activity.
     *
     * @param activity       activity
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    fun startActivity(activity: Activity,
                      clz: Class<*>,
                      vararg sharedElements: View) {
        startActivity(activity, null, activity.packageName, clz.name,
                getOptionsBundle(activity, sharedElements))
    }

    /**
     * Start the activity.
     *
     * @param activity  activity
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param extras The Bundle of extras to add to this intent.
     * @param clz    The activity class.
     */
    fun startActivity(extras: Bundle,
                      clz: Class<*>) {
        val context = activityOrApp
        startActivity(context, extras, context.packageName, clz.name, null)
    }

    /**
     * Start the activity.
     *
     * @param extras  The Bundle of extras to add to this intent.
     * @param clz     The activity class.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivity(extras: Bundle,
                      clz: Class<*>,
                      options: Bundle) {
        val context = activityOrApp
        startActivity(context, extras, context.packageName, clz.name, options)
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity activity
     * @param clz      The activity class.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      clz: Class<*>) {
        startActivity(activity, extras, activity.packageName, clz.name, null)
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity activity
     * @param clz      The activity class.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      clz: Class<*>,
                      options: Bundle) {
        startActivity(activity, extras, activity.packageName, clz.name, options)
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       activity
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      clz: Class<*>,
                      vararg sharedElements: View) {
        startActivity(activity, extras, activity.packageName, clz.name,
                getOptionsBundle(activity, sharedElements))
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param activity  activity
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     */
    fun startActivity(pkg: String,
                      cls: String) {
        startActivity(activityOrApp, null, pkg, cls, null)
    }

    /**
     * Start the activity.
     *
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivity(pkg: String,
                      cls: String,
                      options: Bundle?) {
        startActivity(activityOrApp, null, pkg, cls, options)
    }

    /**
     * Start the activity.
     *
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param activity activity
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    fun startActivity(activity: Activity,
                      pkg: String,
                      cls: String) {
        startActivity(activity, null, pkg, cls, null)
    }

    /**
     * Start the activity.
     *
     * @param activity activity
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivity(activity: Activity,
                      pkg: String,
                      cls: String,
                      options: Bundle?) {
        startActivity(activity, null, pkg, cls, options)
    }

    /**
     * Start the activity.
     *
     * @param activity       activity
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    fun startActivity(activity: Activity,
                      pkg: String,
                      cls: String,
                      vararg sharedElements: View) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements))
    }

    /**
     * Start the activity.
     *
     * @param activity  activity
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param extras The Bundle of extras to add to this intent.
     * @param pkg    The name of the package.
     * @param cls    The name of the class.
     */
    fun startActivity(extras: Bundle,
                      pkg: String,
                      cls: String) {
        startActivity(activityOrApp, extras, pkg, cls, null)
    }

    /**
     * Start the activity.
     *
     * @param extras  The Bundle of extras to add to this intent.
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivity(extras: Bundle,
                      pkg: String,
                      cls: String,
                      options: Bundle) {
        startActivity(activityOrApp, extras, pkg, cls, options)
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param activity activity
     * @param extras   The Bundle of extras to add to this intent.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      pkg: String,
                      cls: String) {
        startActivity(activity, extras, pkg, cls, null)
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity activity
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      pkg: String,
                      cls: String,
                      options: Bundle) {
        startActivity(activity, extras, pkg, cls, options)
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       activity
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    fun startActivity(extras: Bundle,
                      activity: Activity,
                      pkg: String,
                      cls: String,
                      vararg sharedElements: View) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements))
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param intent The description of the activity to start.
     */
    fun startActivity(intent: Intent) {
        startActivity(intent, activityOrApp, null)
    }

    /**
     * Start the activity.
     *
     * @param intent  The description of the activity to start.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivity(intent: Intent,
                      options: Bundle) {
        startActivity(intent, activityOrApp, options)
    }

    /**
     * Start the activity.
     *
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start the activity.
     *
     * @param activity activity
     * @param intent   The description of the activity to start.
     */
    fun startActivity(activity: Activity,
                      intent: Intent) {
        startActivity(intent, activity, null)
    }

    /**
     * Start the activity.
     *
     * @param activity activity
     * @param intent   The description of the activity to start.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivity(activity: Activity,
                      intent: Intent,
                      options: Bundle?) {
        startActivity(intent, activity, options)
    }

    /**
     * Start the activity.
     *
     * @param activity       activity
     * @param intent         The description of the activity to start.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    fun startActivity(activity: Activity,
                      intent: Intent,
                      vararg sharedElements: View) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements))
    }

    /**
     * Start the activity.
     *
     * @param activity  activity
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     */
    fun startActivities(intents: Array<Intent>) {
        startActivities(intents, activityOrApp, null)
    }

    /**
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     * @param options Additional options for how the Activity should be started.
     */
    fun startActivities(intents: Array<Intent>,
                        options: Bundle?) {
        startActivities(intents, activityOrApp, options)
    }

    /**
     * Start activities.
     *
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start activities.
     *
     * @param activity activity
     * @param intents  The descriptions of the activities to start.
     */
    fun startActivities(activity: Activity,
                        intents: Array<Intent>) {
        startActivities(intents, activity, null)
    }

    /**
     * Start activities.
     *
     * @param activity activity
     * @param intents  The descriptions of the activities to start.
     * @param options  Additional options for how the Activity should be started.
     */
    fun startActivities(activity: Activity,
                        intents: Array<Intent>,
                        options: Bundle?) {
        startActivities(intents, activity, options)
    }

    /**
     * Start activities.
     *
     * @param activity  activity
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Start home activity.
     */
    fun startHomeActivity() {
        val homeIntent = Intent(Intent.ACTION_MAIN)
        homeIntent.addCategory(Intent.CATEGORY_HOME)
        startActivity(homeIntent)
    }

    /**
     * Return the list of activity.
     *
     * @return the list of activity
     */
    val activityList: List<Activity>
        get() = Utils.sActivityList

    /**
     * Return the name of launcher activity.
     *
     * @return the name of launcher activity
     */
    val launcherActivity: String
        get() = getLauncherActivity(Utils.app.packageName)

    /**
     * Return the name of launcher activity.
     *
     * @param packageName The name of the package.
     * @return the name of launcher activity
     */
    fun getLauncherActivity(packageName: String): String {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pm = Utils.app.packageManager
        val info = pm.queryIntentActivities(intent, 0)
        for (aInfo in info) {
            if (aInfo.activityInfo.packageName == packageName) {
                return aInfo.activityInfo.name
            }
        }
        return "no " + packageName
    }

    /**
     * Return the top activity in activity's stack.
     *
     * @return the top activity in activity's stack
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
     * Return whether the activity exists in activity's stack.
     *
     * @param activity activity
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isActivityExistsInStack(activity: Activity): Boolean {
        val activities = Utils.sActivityList
        return activities.contains(activity)
    }

    /**
     * Return whether the activity exists in activity's stack.
     *
     * @param clz The activity class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    fun isActivityExistsInStack(clz: Class<*>): Boolean {
        val activities = Utils.sActivityList
        return activities.any { it.javaClass == clz }
    }

    /**
     * Finish the activity.
     *
     * @param activity   activity
     * @param isLoadAnim Whether use animation for the outgoing activity.
     */

    fun finishActivity(activity: Activity, isLoadAnim: Boolean = false) {
        activity.finish()
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0)
        }
    }

    /**
     * Finish the activity.
     *
     * @param activity  activity
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    fun finishActivity(activity: Activity,
                       @AnimRes enterAnim: Int,
                       @AnimRes exitAnim: Int) {
        activity.finish()
        activity.overridePendingTransition(enterAnim, exitAnim)
    }

    /**
     * Finish the activity.
     *
     * @param clz        The activity class.
     * @param isLoadAnim Whether use animation for the outgoing activity.
     */
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
     * Finish the activity.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
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
     * Finish to the activity.
     *
     * @param activity      activity
     * @param isIncludeSelf Whether include the activity.
     * @param isLoadAnim    Whether use animation for the outgoing activity.
     */
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
     * Finish to the activity.
     *
     * @param activity      activity
     * @param isIncludeSelf Whether include the activity.
     * @param enterAnim     A resource ID of the animation resource to use for the
     *                      incoming activity.
     * @param exitAnim      A resource ID of the animation resource to use for the
     *                      outgoing activity.
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
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf Whether include the activity.
     * @param isLoadAnim    Whether use animation for the outgoing activity.
     */

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
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf Whether include the activity.
     * @param enterAnim     A resource ID of the animation resource to use for the
     *                      incoming activity.
     * @param exitAnim      A resource ID of the animation resource to use for the
     *                      outgoing activity.
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
     * Finish the activities whose type not equals the activity class.
     *
     * @param clz        The activity class.
     * @param isLoadAnim Whether use animation for the outgoing activity.
     */
    fun finishOtherActivities(clz: Class<*>,
                              isLoadAnim: Boolean = false) {
        val activities = Utils.sActivityList
        var flag = false
        activities.indices.reversed()
                .asSequence()
                .map { activities[it] }
                .forEach {
                    if (it.javaClass == clz) {
                        if (flag) {
                            finishActivity(it, isLoadAnim)
                        } else {
                            flag = true
                        }
                    } else {
                        finishActivity(it, isLoadAnim)
                    }
                }
    }

    /**
     * Finish the activities whose type not equals the activity class.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    fun finishOtherActivities(clz: Class<*>,
                              @AnimRes enterAnim: Int,
                              @AnimRes exitAnim: Int) {
        val activities = Utils.sActivityList
        var flag = false
        activities.indices.reversed()
                .asSequence()
                .map { activities[it] }
                .forEach {
                    if (it.javaClass == clz) {
                        if (flag) {
                            finishActivity(it, enterAnim, exitAnim)
                        } else {
                            flag = true
                        }
                    } else {
                        finishActivity(it, enterAnim, exitAnim)
                    }
                }
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param isLoadAnim Whether use animation for the outgoing activity.
     */
    fun finishAllActivities(isLoadAnim: Boolean = false) {
        val activityList = Utils.sActivityList
        for (i in activityList.indices.reversed()) {// remove from top
            val activity = activityList[i]
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish()
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0)
            }
        }
    }

    /**
     * Finish all of activities.
     *
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    fun finishAllActivities(@AnimRes enterAnim: Int,
                            @AnimRes exitAnim: Int) {
        val activityList = Utils.sActivityList
        for (i in activityList.indices.reversed()) {// remove from top
            val activity = activityList[i]
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish()
            activity.overridePendingTransition(enterAnim, exitAnim)
        }
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param isLoadAnim Whether use animation for the outgoing activity.
     */
    fun finishAllActivitiesExceptNewest(isLoadAnim: Boolean = false) {
        val activities = Utils.sActivityList
        for (i in activities.size - 2 downTo 0) {
            finishActivity(activities[i], isLoadAnim)
        }
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    fun finishAllActivitiesExceptNewest(@AnimRes enterAnim: Int,
                                        @AnimRes exitAnim: Int) {
        val activities = Utils.sActivityList
        for (i in activities.size - 2 downTo 0) {
            finishActivity(activities[i], enterAnim, exitAnim)
        }
    }

    /**
     * Return the icon of activity.
     *
     * @param clz The activity class.
     * @return the icon of activity
     */
    fun getActivityIcon(clz: Class<*>): Drawable? {
        return getActivityIcon(ComponentName(Utils.app, clz))
    }

    /**
     * Return the icon of activity.
     *
     * @param activityName The name of activity.
     * @return the icon of activity
     */
    fun getActivityIcon(activityName: ComponentName): Drawable? {
        val pm = Utils.app.packageManager
        try {
            return pm.getActivityIcon(activityName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * Return the logo of activity.
     *
     * @param clz The activity class.
     * @return the logo of activity
     */
    fun getActivityLogo(clz: Class<*>): Drawable? {
        return getActivityLogo(ComponentName(Utils.app, clz))
    }

    /**
     * Return the logo of activity.
     *
     * @param activityName The name of activity.
     * @return the logo of activity
     */
    fun getActivityLogo(activityName: ComponentName): Drawable? {
        val pm = Utils.app.packageManager
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
