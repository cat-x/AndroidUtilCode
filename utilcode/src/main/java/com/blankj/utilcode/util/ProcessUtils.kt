package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/18
 * desc  : 进程相关工具类
</pre> *
 */
class ProcessUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 获取前台线程包名
         *
         * 当不是查看当前 App，且 SDK 大于 21 时，
         * 需添加权限
         * `<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS" />`
         *
         * @return 前台应用包名
         */
        // 有"有权查看使用权限的应用"选项
        val foregroundProcessName: String?
            get() {
                val manager = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                        ?: return null
                val pInfo = manager.runningAppProcesses
                if (pInfo != null && pInfo.size > 0) {
                    for (aInfo in pInfo) {
                        if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                            return aInfo.processName
                        }
                    }
                }
                if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.LOLLIPOP) {
                    val packageManager = Utils.app.getPackageManager()
                    val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                    val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                    Log.i("ProcessUtils", list.toString())
                    if (list.size <= 0) {
                        Log.i("ProcessUtils",
                                "getForegroundProcessName() called" + ": 无\"有权查看使用权限的应用\"选项")
                        return null
                    }
                    try {
                        val info = packageManager.getApplicationInfo(Utils.app.getPackageName(), 0)
                        val aom = Utils.app.getSystemService(Context.APP_OPS_SERVICE) as? AppOpsManager
                        if (aom != null) {
                            if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                            info.uid,
                                            info.packageName) != AppOpsManager.MODE_ALLOWED) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                Utils.app.startActivity(intent)
                            }
                            if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                                            info.uid,
                                            info.packageName) != AppOpsManager.MODE_ALLOWED) {
                                Log.i("ProcessUtils", "没有打开\"有权查看使用权限的应用\"选项")
                                return null
                            }
                        }
                        val usageStatsManager = Utils.app
                                .getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
                        var usageStatsList: List<UsageStats>? = null
                        if (usageStatsManager != null) {
                            val endTime = System.currentTimeMillis()
                            val beginTime = endTime - 86400000 * 7
                            usageStatsList = usageStatsManager
                                    .queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                                            beginTime, endTime)
                        }
                        if (usageStatsList == null || usageStatsList.isEmpty()) return null
                        var recentStats: UsageStats? = null
                        for (usageStats in usageStatsList) {
                            if (recentStats == null || usageStats.lastTimeUsed > recentStats.lastTimeUsed) {
                                recentStats = usageStats
                            }
                        }
                        return if (recentStats == null) null else recentStats.packageName
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                    }

                }
                return null
            }

        /**
         * 获取后台服务进程
         *
         * 需添加权限
         * `<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />`
         *
         * @return 后台服务进程
         */
        val allBackgroundProcesses: Set<String>
            get() {
                val am = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                        ?: return emptySet<String>()
                val info = am.runningAppProcesses
                val set = HashSet<String>()
                if (info != null) {
                    for (aInfo in info) {
                        Collections.addAll(set, *aInfo.pkgList)
                    }
                }
                return set
            }

        /**
         * 杀死所有的后台服务进程
         *
         * 需添加权限
         * `<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />`
         *
         * @return 被暂时杀死的服务集合
         */
        @SuppressLint("MissingPermission")
        fun killAllBackgroundProcesses(): Set<String> {
            val am = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                    ?: return emptySet<String>()
            var info: List<ActivityManager.RunningAppProcessInfo> = am.runningAppProcesses
            val set = HashSet<String>()
            for (aInfo in info) {
                for (pkg in aInfo.pkgList) {
                    am.killBackgroundProcesses(pkg)
                    set.add(pkg)
                }
            }
            info = am.runningAppProcesses
            for (aInfo in info) {
                for (pkg in aInfo.pkgList) {
                    set.remove(pkg)
                }
            }
            return set
        }

        /**
         * 杀死后台服务进程
         *
         * 需添加权限
         * `<uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />`
         *
         * @param packageName 包名
         * @return `true`: 杀死成功<br></br>`false`: 杀死失败
         */
        @SuppressLint("MissingPermission")
        fun killBackgroundProcesses(packageName: String): Boolean {
            val am = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                    ?: return false
            var info: List<ActivityManager.RunningAppProcessInfo>? = am.runningAppProcesses
            if (info == null || info.size == 0) return true
            for (aInfo in info) {
                if (Arrays.asList(*aInfo.pkgList).contains(packageName)) {
                    am.killBackgroundProcesses(packageName)
                }
            }
            info = am.runningAppProcesses
            if (info == null || info.size == 0) return true
            for (aInfo in info) {
                if (Arrays.asList(*aInfo.pkgList).contains(packageName)) {
                    return false
                }
            }
            return true
        }
    }
}
