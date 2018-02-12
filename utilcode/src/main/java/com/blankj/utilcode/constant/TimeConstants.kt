package com.blankj.utilcode.constant

import android.support.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/13
 * desc  : The constants of time
</pre> *
 */
object TimeConstants {

    const val MSEC = 1
    const val SEC = 1000
    const  val MIN = 60000
    const  val HOUR = 3600000
    const  val DAY = 86400000

    @IntDef(MSEC.toLong(), SEC.toLong(), MIN.toLong(), HOUR.toLong(), DAY.toLong())
    @Retention(RetentionPolicy.SOURCE)
    annotation class Unit
}
