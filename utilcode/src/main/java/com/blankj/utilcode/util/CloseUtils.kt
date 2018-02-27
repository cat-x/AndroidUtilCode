package com.blankj.utilcode.util

import java.io.Closeable
import java.io.IOException

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/09
 * desc  : 关闭相关工具类
</pre> *
 */
object CloseUtils {


    /**
     * 关闭 IO
     *
     * @param closeables closeables
     */
    fun <T : Closeable> closeIO(vararg closeables: T?) {
        if (closeables.isEmpty()) return
        closeables.filterNotNull()
                .forEach {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
    }

    /**
     * 安静关闭 IO
     *
     * @param closeables closeables
     */
    fun <T : Closeable> closeIOQuietly(vararg closeables: T?) {
        if (closeables.isEmpty()) return
        closeables.filterNotNull()
                .forEach {
                    try {
                        it.close()
                    } catch (ignored: IOException) {
                    }
                }
    }

}
