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
class CloseUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * 关闭 IO
         *
         * @param closeables closeables
         */
        fun <T : Closeable> closeIO(vararg closeables: T?) {
            if (closeables.isEmpty()) return
            for (closeable in closeables) {
                if (closeable != null) {
                    try {
                        closeable.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

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
            for (closeable in closeables) {
                if (closeable != null) {
                    try {
                        closeable.close()
                    } catch (ignored: IOException) {
                    }

                }
            }
        }
    }
}
