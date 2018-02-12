package com.blankj.utilcode.constant

import android.support.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/13
 * desc  : The constants of memory
</pre> *
 */
object MemoryConstants {

    const val BYTE = 1
    const val KB = 1024
    const val MB = 1048576
    const val GB = 1073741824

    @IntDef(BYTE.toLong(), KB.toLong(), MB.toLong(), GB.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}
