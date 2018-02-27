package com.blankj.utilcode.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/02
 * desc  : 服务相关工具类
</pre> *
 */
object ServiceUtils {


    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    val allRunningService: Set<String>?
        get() {
            val am = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                    ?: return Collections.emptySet()
            val info = am.getRunningServices(0x7FFFFFFF)
            if (info == null || info.size == 0) return null
            return info.mapTo(HashSet()) { it.service.className }
        }

    /**
     * 启动服务
     *
     * @param className 完整包名的服务类名
     */
    fun startService(className: String) {
        try {
            startService(Class.forName(className))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 启动服务
     *
     * @param cls 服务类
     */
    fun startService(cls: Class<*>) {
        val intent = Intent(Utils.app, cls)
        Utils.app.startService(intent)
    }

    /**
     * 停止服务
     *
     * @param className 完整包名的服务类名
     * @return `true`: 停止成功<br></br>`false`: 停止失败
     */
    fun stopService(className: String): Boolean {
        try {
            return stopService(Class.forName(className))
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 停止服务
     *
     * @param cls 服务类
     * @return `true`: 停止成功<br></br>`false`: 停止失败
     */
    fun stopService(cls: Class<*>): Boolean {
        val intent = Intent(Utils.app, cls)
        return Utils.app.stopService(intent)
    }

    /**
     * 绑定服务
     *
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *
     *  * [Context.BIND_AUTO_CREATE]
     *  * [Context.BIND_DEBUG_UNBIND]
     *  * [Context.BIND_NOT_FOREGROUND]
     *  * [Context.BIND_ABOVE_CLIENT]
     *  * [Context.BIND_ALLOW_OOM_MANAGEMENT]
     *  * [Context.BIND_WAIVE_PRIORITY]
     *
     */
    fun bindService(className: String,
                    conn: ServiceConnection,
                    flags: Int) {
        try {
            bindService(Class.forName(className), conn, flags)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 绑定服务
     *
     * @param cls   服务类
     * @param conn  服务连接对象
     * @param flags 绑定选项
     *
     *  * [Context.BIND_AUTO_CREATE]
     *  * [Context.BIND_DEBUG_UNBIND]
     *  * [Context.BIND_NOT_FOREGROUND]
     *  * [Context.BIND_ABOVE_CLIENT]
     *  * [Context.BIND_ALLOW_OOM_MANAGEMENT]
     *  * [Context.BIND_WAIVE_PRIORITY]
     *
     */
    fun bindService(cls: Class<*>,
                    conn: ServiceConnection,
                    flags: Int) {
        val intent = Intent(Utils.app, cls)
        Utils.app.bindService(intent, conn, flags)
    }

    /**
     * 解绑服务
     *
     * @param conn 服务连接对象
     */
    fun unbindService(conn: ServiceConnection) {
        Utils.app.unbindService(conn)
    }

    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return `true`: 是<br></br>`false`: 否
     */
    fun isServiceRunning(className: String): Boolean {
        val am = Utils.app.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
                ?: return false
        val info = am.getRunningServices(0x7FFFFFFF)
        if (info == null || info.size == 0) return false
        return info.any { className == it.service.className }
    }
}

