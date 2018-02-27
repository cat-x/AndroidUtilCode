package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.PrintWriter
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : 崩溃相关工具类
</pre> *
 */
object CrashUtils {


    interface OnCrashListener {
        fun onCrash(e: Throwable?)
    }


    private var defaultDir: String? = null
    private var dir: String? = null
    private var versionName: String? = null
    private var versionCode: Int = 0

    private var sExecutor: ExecutorService? = null

    private val FILE_SEP = System.getProperty("file.separator")
    @SuppressLint("SimpleDateFormat")
    private val FORMAT = SimpleDateFormat("MM-dd HH-mm-ss")

    private val CRASH_HEAD: String

    private val DEFAULT_UNCAUGHT_EXCEPTION_HANDLER: UncaughtExceptionHandler?
    private val UNCAUGHT_EXCEPTION_HANDLER: UncaughtExceptionHandler

    private var sOnCrashListener: OnCrashListener? = null

    init {
        try {
            val pi = Utils.app
                    .packageManager
                    .getPackageInfo(Utils.app.packageName, 0)
            if (pi != null) {
                versionName = pi.versionName
                versionCode = pi.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        CRASH_HEAD = "************* Crash Log Head ****************" +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +// 设备厂商

                "\nDevice Model       : " + Build.MODEL +// 设备型号

                "\nAndroid Version    : " + Build.VERSION.RELEASE +// 系统版本

                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +// SDK 版本

                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Crash Log Head ****************\n\n"

        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler()

        UNCAUGHT_EXCEPTION_HANDLER = UncaughtExceptionHandler { t, e ->
            if (e == null) {
                if (DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(t, null)
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid())
                    System.exit(1)
                }
                return@UncaughtExceptionHandler
            }
            if (sOnCrashListener != null) {
                sOnCrashListener!!.onCrash(e)
            }
            val now = Date(System.currentTimeMillis())
            val fileName = FORMAT.format(now) + ".txt"
            val fullPath = (if (dir == null) defaultDir else dir) + fileName
            if (!createOrExistsFile(fullPath)) return@UncaughtExceptionHandler
            if (sExecutor == null) {
                sExecutor = Executors.newSingleThreadExecutor()
            }
            sExecutor!!.execute {
                var pw: PrintWriter? = null
                try {
                    pw = PrintWriter(FileWriter(fullPath, false))
                    pw.write(CRASH_HEAD)
                    e.printStackTrace(pw)
                    var cause: Throwable? = e.cause
                    while (cause != null) {
                        cause.printStackTrace(pw)
                        cause = cause.cause
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    if (pw != null) {
                        pw.close()
                    }
                }
            }
            DEFAULT_UNCAUGHT_EXCEPTION_HANDLER?.uncaughtException(t, e)
        }
    }

    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
     *
     * @param crashDir 崩溃文件存储目录
     */
    fun init(crashDir: File) {
        init(crashDir.absolutePath, null)
    }

    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
     *
     * @param onCrashListener 崩溃监听事件
     */
    fun init(onCrashListener: OnCrashListener) {
        init("", onCrashListener)
    }

    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
     *
     * @param crashDir        崩溃文件存储目录
     * @param onCrashListener 崩溃监听事件
     */
    fun init(crashDir: File, onCrashListener: OnCrashListener) {
        init(crashDir.absolutePath, onCrashListener)
    }

    /**
     * 初始化
     *
     * 需添加权限 `<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />`
     *
     * @param crashDir        崩溃文件存储目录
     * @param onCrashListener 崩溃监听事件
     */
    @JvmOverloads
    fun init(crashDir: String = "", onCrashListener: OnCrashListener? = null) {
        if (isSpace(crashDir)) {
            dir = null
        } else {
            dir = if (crashDir.endsWith(FILE_SEP)) crashDir else crashDir + FILE_SEP
        }
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && Utils.app.externalCacheDir != null)
            defaultDir = Utils.app.externalCacheDir.path + FILE_SEP + "crash" + FILE_SEP
        else {
            defaultDir = Utils.app.cacheDir.path + FILE_SEP + "crash" + FILE_SEP
        }
        sOnCrashListener = onCrashListener
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER)
    }

    private fun createOrExistsFile(filePath: String): Boolean {
        val file = File(filePath)
        if (file.exists()) return file.isFile
        if (!createOrExistsDir(file.parentFile)) return false
        try {
            return file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

    }

    private fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}
