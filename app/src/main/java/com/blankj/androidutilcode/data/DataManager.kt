package com.blankj.androidutilcode.data

import com.blankj.utilcode.util.SPUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/08
 * desc  : 数据管理器
</pre> *
 */
object DataManager {

    private val SP_UTILS = SPUtils.getInstance("demo")

    val string: String
        get() = SP_UTILS.getString("STRING")

    val int: String
        get() = SP_UTILS.getInt("INT").toString()

    val long: String
        get() = SP_UTILS.getLong("LONG").toString()

    val float: String
        get() = SP_UTILS.getFloat("FLOAT").toString()

    val boolean: String
        get() = SP_UTILS.getBoolean("BOOLEAN").toString()

    fun putString() {
        SP_UTILS.put("STRING", "string")
    }

    fun putInt() {
        SP_UTILS.put("INT", 21)
    }

    fun putLong() {
        SP_UTILS.put("LONG", java.lang.Long.MAX_VALUE)
    }

    fun putFloat() {
        SP_UTILS.put("FLOAT", Math.PI.toFloat())
    }

    fun putBoolean() {
        SP_UTILS.put("BOOLEAN", true)
    }

    fun clear() {
        SP_UTILS.clear()
    }

    fun sp2String(): String {
        val sb = StringBuilder()
        val map = SP_UTILS.all
        for ((key, value) in map) {
            sb.append(key)
                    .append(": ")
                    .append(value)
                    .append("\n")
        }
        return sb.toString()
    }

}
