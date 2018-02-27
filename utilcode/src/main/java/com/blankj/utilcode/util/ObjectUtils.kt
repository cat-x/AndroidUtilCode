package com.blankj.utilcode.util

import android.os.Build
import android.support.v4.util.LongSparseArray
import android.support.v4.util.SimpleArrayMap
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray

import java.lang.reflect.Array

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/12/24
 * desc  : 对象相关工具类
</pre> *
 */
object ObjectUtils {


    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return `true`: 为空<br></br>`false`: 不为空
     */
    fun isEmpty(obj: Any?): Boolean {
        if (obj == null) {
            return true
        }
        if (obj is CharSequence && obj.toString().isEmpty()) {
            return true
        }
        if (obj.javaClass.isArray && Array.getLength(obj) == 0) {
            return true
        }
        if (obj is Collection<*> && obj.isEmpty()) {
            return true
        }
        if (obj is Map<*, *> && obj.isEmpty()) {
            return true
        }
        if (obj is SimpleArrayMap<*, *> && obj.isEmpty) {
            return true
        }
        if (obj is SparseArray<*> && obj.size() == 0) {
            return true
        }
        if (obj is SparseBooleanArray && obj.size() == 0) {
            return true
        }
        if (obj is SparseIntArray && obj.size() == 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj is SparseLongArray && obj.size() == 0) {
                return true
            }
        }
        if (obj is LongSparseArray<*> && obj.size() == 0) {
            return true
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (obj is android.util.LongSparseArray<*> && obj.size() == 0) {
                return true
            }
        }
        return false
    }

    /**
     * 判断对象是否非空
     *
     * @param obj 对象
     * @return `true`: 非空<br></br>`false`: 空
     */
    fun isNotEmpty(obj: Any): Boolean {
        return !isEmpty(obj)
    }

    /**
     * 判断对象是否相等
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return `true`: 相等<br></br>`false`: 不相等
     */
    fun equals(o1: Any?, o2: Any): Boolean {
        return o1 === o2 || (o1 != null && o1 == o2)
    }

    /**
     * 检查对象非空
     *
     * @param object  对象
     * @param message 报错
     * @param <T>     范型
     * @return 非空对象
    </T> */
    fun <T> requireNonNull(`object`: T?, message: String): T {
        if (`object` == null) {
            throw NullPointerException(message)
        }
        return `object`
    }

    /**
     * 获取非空或默认对象
     *
     * @param object        对象
     * @param defaultObject 默认值
     * @param <T>           范型
     * @return 非空或默认对象
    </T> */
    fun <T> getOrDefault(`object`: T?, defaultObject: T): T {
        return `object` ?: defaultObject
    }

    /**
     * 获取对象哈希值
     *
     * @param o 对象
     * @return 哈希值
     */
    fun hashCode(o: Any?): Int {
        return o?.hashCode() ?: 0
    }
}

