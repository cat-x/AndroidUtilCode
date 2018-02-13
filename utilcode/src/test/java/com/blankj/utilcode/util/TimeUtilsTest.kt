package com.blankj.utilcode.util

import com.blankj.utilcode.constant.TimeConstants
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/12
 * desc  : TimeUtils 单元测试
</pre> *
 */
class TimeUtilsTest {

    private val defaultFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val mFormat = SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault())

    private val timeMillis = 1493887049000L
    private val timeDate = Date(timeMillis)
    private val timeString = defaultFormat.format(timeDate)
    private val timeStringFormat = mFormat.format(timeDate)
    private val tomorrowTimeMillis = 1493973449000L
    private val tomorrowTimeDate = Date(tomorrowTimeMillis)
    private val tomorrowTimeString = defaultFormat.format(tomorrowTimeDate)
    private val tomorrowTimeStringFormat = mFormat.format(tomorrowTimeDate)
    private val delta: Long = 5L// 允许误差5ms

    private fun assertEquals(l: Long, millisByNow: Long, delta: Long) {
        assertEquals(l.toDouble(), millisByNow.toDouble(), delta.toDouble())
    }

    @Test
    @Throws(Exception::class)
    fun millis2String() {
        assertEquals(timeString, TimeUtils.millis2String(timeMillis))
        assertEquals(timeStringFormat, TimeUtils.millis2String(timeMillis, mFormat))
    }

    @Test
    @Throws(Exception::class)
    fun string2Millis() {
        assertEquals(timeMillis, TimeUtils.string2Millis(timeString))
        assertEquals(timeMillis, TimeUtils.string2Millis(timeStringFormat, mFormat))
    }

    @Test
    @Throws(Exception::class)
    fun string2Date() {
        assertEquals(timeDate, TimeUtils.string2Date(timeString))
        assertEquals(timeDate, TimeUtils.string2Date(timeStringFormat, mFormat))
    }

    @Test
    @Throws(Exception::class)
    fun date2String() {
        assertEquals(timeString, TimeUtils.date2String(timeDate))
        assertEquals(timeStringFormat, TimeUtils.date2String(timeDate, mFormat))
    }

    @Test
    @Throws(Exception::class)
    fun date2Millis() {
        assertEquals(timeMillis, TimeUtils.date2Millis(timeDate))
    }

    @Test
    @Throws(Exception::class)
    fun millis2Date() {
        assertEquals(timeDate, TimeUtils.millis2Date(timeMillis))
    }

    @Test
    @Throws(Exception::class)
    fun getTimeSpan() {
        val testTimeMillis = timeMillis + 120 * TimeConstants.SEC
        val testTimeString = TimeUtils.millis2String(testTimeMillis)
        val testTimeStringFormat = TimeUtils.millis2String(testTimeMillis, mFormat)
        val testTimeDate = TimeUtils.millis2Date(testTimeMillis)
        assertEquals(120, TimeUtils.getTimeSpan(timeString, testTimeString, TimeConstants.SEC))
        assertEquals(2, TimeUtils.getTimeSpan(timeStringFormat, testTimeStringFormat, mFormat, TimeConstants.MIN))
        assertEquals(2, TimeUtils.getTimeSpan(timeDate, testTimeDate, TimeConstants.MIN))
        assertEquals(120, TimeUtils.getTimeSpan(timeMillis, testTimeMillis, TimeConstants.SEC))
    }

    @Test
    @Throws(Exception::class)
    fun getFitTimeSpan() {
        val testTimeMillis = timeMillis + 10 * TimeConstants.DAY + 10 * TimeConstants.MIN + 10 * TimeConstants.SEC
        val testTimeString = TimeUtils.millis2String(testTimeMillis)
        val testTimeStringFormat = TimeUtils.millis2String(testTimeMillis, mFormat)
        val testTimeDate = TimeUtils.millis2Date(testTimeMillis)
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(timeString, testTimeString, 5))
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(timeStringFormat, testTimeStringFormat, mFormat, 5))
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(timeDate, testTimeDate, 5))
        assertEquals("10天10分钟10秒", TimeUtils.getFitTimeSpan(timeMillis, testTimeMillis, 5))
    }

    @Test
    @Throws(Exception::class)
    fun getNowMills() {
        assertEquals(System.currentTimeMillis().toFloat(), TimeUtils.nowMills.toFloat(), delta.toFloat())
    }

    @Test
    @Throws(Exception::class)
    fun getNowString() {
        assertEquals(System.currentTimeMillis(), TimeUtils.string2Millis(TimeUtils.nowString), delta)
        assertEquals(System.currentTimeMillis().toFloat(), TimeUtils.string2Millis(TimeUtils.getNowString(mFormat), mFormat).toFloat(), delta.toFloat())
    }

    @Test
    @Throws(Exception::class)
    fun getNowDate() {
        assertEquals(System.currentTimeMillis().toFloat(), TimeUtils.date2Millis(TimeUtils.nowDate).toFloat(), delta.toFloat())
    }

    @Test
    @Throws(Exception::class)
    fun getTimeSpanByNow() {
        assertEquals(0f, TimeUtils.getTimeSpanByNow(TimeUtils.nowString, TimeConstants.MSEC).toFloat(), delta.toFloat())
        assertEquals(0f, TimeUtils.getTimeSpanByNow(TimeUtils.getNowString(mFormat), mFormat, TimeConstants.MSEC).toFloat(), delta.toFloat())
        assertEquals(0f, TimeUtils.getTimeSpanByNow(TimeUtils.nowDate, TimeConstants.MSEC).toFloat(), delta.toFloat())
        assertEquals(0f, TimeUtils.getTimeSpanByNow(TimeUtils.nowMills, TimeConstants.MSEC).toFloat(), delta.toFloat())
    }

    @Test
    @Throws(Exception::class)
    fun getFitTimeSpanByNow() {
        val spanMillis = 6 * TimeConstants.DAY + 6 * TimeConstants.HOUR + 6 * TimeConstants.MIN + 6 * TimeConstants.SEC
        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis), 4))
        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis, mFormat), mFormat, 4))
        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(TimeUtils.millis2Date(System.currentTimeMillis() + spanMillis), 4))
        assertEquals("6天6小时6分钟6秒", TimeUtils.getFitTimeSpanByNow(System.currentTimeMillis() + spanMillis, 4))
    }

    @Test
    @Throws(Exception::class)
    fun getFriendlyTimeSpanByNow() {
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.nowString))
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.getNowString(mFormat), mFormat))
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.nowDate))
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.nowMills))
        assertEquals("1秒前", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.nowMills - TimeConstants.SEC))
        assertEquals("1分钟前", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.nowMills - TimeConstants.MIN))
    }

    @Test
    @Throws(Exception::class)
    fun getMillis() {
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeMillis, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeString, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeStringFormat, mFormat, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeMillis, TimeUtils.getMillis(timeDate, 1, TimeConstants.DAY))
    }

    @Test
    @Throws(Exception::class)
    fun getString() {
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeMillis, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeMillis, mFormat, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeString, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeStringFormat, mFormat, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeString, TimeUtils.getString(timeDate, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeStringFormat, TimeUtils.getString(timeDate, mFormat, 1, TimeConstants.DAY))
    }

    @Test
    @Throws(Exception::class)
    fun getDate() {
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeMillis, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeString, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeStringFormat, mFormat, 1, TimeConstants.DAY))
        assertEquals(tomorrowTimeDate, TimeUtils.getDate(timeDate, 1, TimeConstants.DAY))
    }

    @Test
    @Throws(Exception::class)
    fun getMillisByNow() {
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, TimeUtils.getMillisByNow(1, TimeConstants.DAY), delta)
    }


    @Test
    @Throws(Exception::class)
    fun getStringByNow() {
        var tomorrowMillis = TimeUtils.string2Millis(TimeUtils.getStringByNow(1, TimeConstants.DAY))
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, tomorrowMillis, delta)
        tomorrowMillis = TimeUtils.string2Millis(TimeUtils.getStringByNow(1, mFormat, TimeConstants.DAY), mFormat)
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, tomorrowMillis, delta)
    }

    @Test
    @Throws(Exception::class)
    fun getDateByNow() {
        val tomorrowMillis = TimeUtils.date2Millis(TimeUtils.getDateByNow(1, TimeConstants.DAY))
        assertEquals(System.currentTimeMillis() + TimeConstants.DAY, TimeUtils.getMillisByNow(1, TimeConstants.DAY), delta)
    }

    @Test
    @Throws(Exception::class)
    fun isToday() {
        val todayTimeMillis = System.currentTimeMillis()
        val todayTimeString = TimeUtils.millis2String(todayTimeMillis)
        val todayTimeStringFormat = TimeUtils.millis2String(todayTimeMillis, mFormat)
        val todayTimeDate = TimeUtils.millis2Date(todayTimeMillis)
        val tomorrowTimeMillis = todayTimeMillis + TimeConstants.DAY
        val tomorrowTimeString = TimeUtils.millis2String(tomorrowTimeMillis)
        val tomorrowTimeDate = TimeUtils.millis2Date(tomorrowTimeMillis)
        assertTrue(TimeUtils.isToday(todayTimeString))
        assertTrue(TimeUtils.isToday(todayTimeStringFormat, mFormat))
        assertTrue(TimeUtils.isToday(todayTimeDate))
        assertTrue(TimeUtils.isToday(todayTimeMillis))
        assertFalse(TimeUtils.isToday(tomorrowTimeString))
        assertFalse(TimeUtils.isToday(tomorrowTimeStringFormat, mFormat))
        assertFalse(TimeUtils.isToday(tomorrowTimeDate))
        assertFalse(TimeUtils.isToday(tomorrowTimeMillis))
    }

    @Test
    @Throws(Exception::class)
    fun isLeapYear() {
        assertFalse(TimeUtils.isLeapYear(timeString))
        assertFalse(TimeUtils.isLeapYear(timeStringFormat, mFormat))
        assertFalse(TimeUtils.isLeapYear(timeDate))
        assertFalse(TimeUtils.isLeapYear(timeMillis))
        assertTrue(TimeUtils.isLeapYear(2016))
        assertFalse(TimeUtils.isLeapYear(2017))
    }

    @Test
    @Throws(Exception::class)
    fun getChineseWeek() {
        assertEquals("星期四", TimeUtils.getChineseWeek(timeString))
        assertEquals("星期四", TimeUtils.getChineseWeek(timeStringFormat, mFormat))
        assertEquals("星期四", TimeUtils.getChineseWeek(timeDate))
        assertEquals("星期四", TimeUtils.getChineseWeek(timeMillis))
    }

    @Test
    @Throws(Exception::class)
    fun getUSWeek() {
        assertEquals("Thursday", TimeUtils.getUSWeek(timeString))
        assertEquals("Thursday", TimeUtils.getUSWeek(timeStringFormat, mFormat))
        assertEquals("Thursday", TimeUtils.getUSWeek(timeDate))
        assertEquals("Thursday", TimeUtils.getUSWeek(timeMillis))
    }

    @Test
    @Throws(Exception::class)
    fun getWeekIndex() {
        assertEquals(5, TimeUtils.getWeekIndex(timeString).toLong())
        assertEquals(5, TimeUtils.getWeekIndex(timeStringFormat, mFormat).toLong())
        assertEquals(5, TimeUtils.getWeekIndex(timeDate).toLong())
        assertEquals(5, TimeUtils.getWeekIndex(timeMillis).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getWeekOfMonth() {
        assertEquals(1, TimeUtils.getWeekOfMonth(timeString).toLong())
        assertEquals(1, TimeUtils.getWeekOfMonth(timeStringFormat, mFormat).toLong())
        assertEquals(1, TimeUtils.getWeekOfMonth(timeDate).toLong())
        assertEquals(1, TimeUtils.getWeekOfMonth(timeMillis).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getWeekOfYear() {
        assertEquals(18, TimeUtils.getWeekOfYear(timeString).toLong())
        assertEquals(18, TimeUtils.getWeekOfYear(timeStringFormat, mFormat).toLong())
        assertEquals(18, TimeUtils.getWeekOfYear(timeDate).toLong())
        assertEquals(18, TimeUtils.getWeekOfYear(timeMillis).toLong())
    }

    @Test
    @Throws(Exception::class)
    fun getChineseZodiac() {
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeString))
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeStringFormat, mFormat))
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeDate))
        assertEquals("鸡", TimeUtils.getChineseZodiac(timeMillis))
        assertEquals("鸡", TimeUtils.getChineseZodiac(2017))
    }

    @Test
    @Throws(Exception::class)
    fun getZodiac() {
        assertEquals("金牛座", TimeUtils.getZodiac(timeString))
        assertEquals("金牛座", TimeUtils.getZodiac(timeStringFormat, mFormat))
        assertEquals("金牛座", TimeUtils.getZodiac(timeDate))
        assertEquals("金牛座", TimeUtils.getZodiac(timeMillis))
        assertEquals("狮子座", TimeUtils.getZodiac(8, 16))
    }
}