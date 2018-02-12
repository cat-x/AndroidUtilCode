package com.blankj.utilcode.util;

import com.blankj.utilcode.constant.TimeConstants;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/12
 *     desc  : TimeUtils 单元测试
 * </pre>
 */
public class TimeUtilsTest {

    private final DateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private final DateFormat mFormat       = new SimpleDateFormat("yyyy MM dd HH:mm:ss", Locale.getDefault());

    private final long   timeMillis               = 1493887049000L;
    private final Date   timeDate                 = new Date(timeMillis);
    private final String timeString               = defaultFormat.format(timeDate);
    private final String timeStringFormat         = mFormat.format(timeDate);
    private final long   tomorrowTimeMillis       = 1493973449000L;
    private final Date   tomorrowTimeDate         = new Date(tomorrowTimeMillis);
    private final String tomorrowTimeString       = defaultFormat.format(tomorrowTimeDate);
    private final String tomorrowTimeStringFormat = mFormat.format(tomorrowTimeDate);
    private final long   delta                    = 5;// 允许误差5ms

    @Test
    public void millis2String() throws Exception {
        assertEquals(timeString, TimeUtils.millis2String(timeMillis));
        assertEquals(timeStringFormat, TimeUtils.Companion.millis2String(timeMillis, mFormat));
    }

    @Test
    public void string2Millis() throws Exception {
        assertEquals(timeMillis, TimeUtils.string2Millis(timeString));
        assertEquals(timeMillis, TimeUtils.Companion.string2Millis(timeStringFormat, mFormat));
    }

    @Test
    public void string2Date() throws Exception {
        assertEquals(timeDate, TimeUtils.string2Date(timeString));
        assertEquals(timeDate, TimeUtils.Companion.string2Date(timeStringFormat, mFormat));
    }

    @Test
    public void date2String() throws Exception {
        assertEquals(timeString, TimeUtils.date2String(timeDate));
        assertEquals(timeStringFormat, TimeUtils.Companion.date2String(timeDate, mFormat));
    }

    @Test
    public void date2Millis() throws Exception {
        assertEquals(timeMillis, TimeUtils.Companion.date2Millis(timeDate));
    }

    @Test
    public void millis2Date() throws Exception {
        assertEquals(timeDate, TimeUtils.Companion.millis2Date(timeMillis));
    }

    @Test
    public void getTimeSpan() throws Exception {
        long testTimeMillis = timeMillis + 120 * TimeConstants.INSTANCE.getSEC();
        String testTimeString = TimeUtils.millis2String(testTimeMillis);
        String testTimeStringFormat = TimeUtils.Companion.millis2String(testTimeMillis, mFormat);
        Date testTimeDate = TimeUtils.Companion.millis2Date(testTimeMillis);
        assertEquals(120, TimeUtils.Companion.getTimeSpan(timeString, testTimeString, TimeConstants.INSTANCE.getSEC()));
        assertEquals(2, TimeUtils.Companion.getTimeSpan(timeStringFormat, testTimeStringFormat, mFormat, TimeConstants.INSTANCE.getMIN()));
        assertEquals(2, TimeUtils.Companion.getTimeSpan(timeDate, testTimeDate, TimeConstants.INSTANCE.getMIN()));
        assertEquals(120, TimeUtils.Companion.getTimeSpan(timeMillis, testTimeMillis, TimeConstants.INSTANCE.getSEC()));
    }

    @Test
    public void getFitTimeSpan() throws Exception {
        long testTimeMillis = timeMillis + 10 * TimeConstants.INSTANCE.getDAY() + 10 * TimeConstants.INSTANCE.getMIN() + 10 * TimeConstants.INSTANCE.getSEC();
        String testTimeString = TimeUtils.millis2String(testTimeMillis);
        String testTimeStringFormat = TimeUtils.Companion.millis2String(testTimeMillis, mFormat);
        Date testTimeDate = TimeUtils.Companion.millis2Date(testTimeMillis);
        assertEquals("10天10分钟10秒", TimeUtils.Companion.getFitTimeSpan(timeString, testTimeString, 5));
        assertEquals("10天10分钟10秒", TimeUtils.Companion.getFitTimeSpan(timeStringFormat, testTimeStringFormat, mFormat, 5));
        assertEquals("10天10分钟10秒", TimeUtils.Companion.getFitTimeSpan(timeDate, testTimeDate, 5));
        assertEquals("10天10分钟10秒", TimeUtils.Companion.getFitTimeSpan(timeMillis, testTimeMillis, 5));
    }

    @Test
    public void getNowMills() throws Exception {
        assertEquals(System.currentTimeMillis(), TimeUtils.Companion.getNowMills(), delta);
    }

    @Test
    public void getNowString() throws Exception {
        assertEquals(System.currentTimeMillis(), TimeUtils.string2Millis(TimeUtils.Companion.getNowString()), delta);
        assertEquals(System.currentTimeMillis(), TimeUtils.Companion.string2Millis(TimeUtils.Companion.getNowString(mFormat), mFormat), delta);
    }

    @Test
    public void getNowDate() throws Exception {
        assertEquals(System.currentTimeMillis(), TimeUtils.Companion.date2Millis(TimeUtils.Companion.getNowDate()), delta);
    }

    @Test
    public void getTimeSpanByNow() throws Exception {
        assertEquals(0, TimeUtils.Companion.getTimeSpanByNow(TimeUtils.Companion.getNowString(), TimeConstants.INSTANCE.getMSEC()), delta);
        assertEquals(0, TimeUtils.Companion.getTimeSpanByNow(TimeUtils.Companion.getNowString(mFormat), mFormat, TimeConstants.INSTANCE.getMSEC()), delta);
        assertEquals(0, TimeUtils.Companion.getTimeSpanByNow(TimeUtils.Companion.getNowDate(), TimeConstants.INSTANCE.getMSEC()), delta);
        assertEquals(0, TimeUtils.Companion.getTimeSpanByNow(TimeUtils.Companion.getNowMills(), TimeConstants.INSTANCE.getMSEC()), delta);
    }

    @Test
    public void getFitTimeSpanByNow() throws Exception {
        long spanMillis = 6 * TimeConstants.INSTANCE.getDAY() + 6 * TimeConstants.INSTANCE.getHOUR() + 6 * TimeConstants.INSTANCE.getMIN() + 6 * TimeConstants.INSTANCE.getSEC();
        assertEquals("6天6小时6分钟6秒", TimeUtils.Companion.getFitTimeSpanByNow(TimeUtils.millis2String(System.currentTimeMillis() + spanMillis), 4));
        assertEquals("6天6小时6分钟6秒", TimeUtils.Companion.getFitTimeSpanByNow(TimeUtils.Companion.millis2String(System.currentTimeMillis() + spanMillis, mFormat), mFormat, 4));
        assertEquals("6天6小时6分钟6秒", TimeUtils.Companion.getFitTimeSpanByNow(TimeUtils.Companion.millis2Date(System.currentTimeMillis() + spanMillis), 4));
        assertEquals("6天6小时6分钟6秒", TimeUtils.Companion.getFitTimeSpanByNow(System.currentTimeMillis() + spanMillis, 4));
    }

    @Test
    public void getFriendlyTimeSpanByNow() throws Exception {
        assertEquals("刚刚", TimeUtils.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowString()));
        assertEquals("刚刚", TimeUtils.Companion.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowString(mFormat), mFormat));
        assertEquals("刚刚", TimeUtils.Companion.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowDate()));
        assertEquals("刚刚", TimeUtils.Companion.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowMills()));
        assertEquals("1秒前", TimeUtils.Companion.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowMills() - TimeConstants.INSTANCE.getSEC()));
        assertEquals("1分钟前", TimeUtils.Companion.getFriendlyTimeSpanByNow(TimeUtils.Companion.getNowMills() - TimeConstants.INSTANCE.getMIN()));
    }

    @Test
    public void getMillis() throws Exception {
        assertEquals(tomorrowTimeMillis, TimeUtils.Companion.getMillis(timeMillis, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeMillis, TimeUtils.Companion.getMillis(timeString, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeMillis, TimeUtils.Companion.getMillis(timeStringFormat, mFormat, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeMillis, TimeUtils.Companion.getMillis(timeDate, 1, TimeConstants.INSTANCE.getDAY()));
    }

    @Test
    public void getString() throws Exception {
        assertEquals(tomorrowTimeString, TimeUtils.Companion.getString(timeMillis, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.Companion.getString(timeMillis, mFormat, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeString, TimeUtils.Companion.getString(timeString, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.Companion.getString(timeStringFormat, mFormat, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeString, TimeUtils.Companion.getString(timeDate, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeStringFormat, TimeUtils.Companion.getString(timeDate, mFormat, 1, TimeConstants.INSTANCE.getDAY()));
    }

    @Test
    public void getDate() throws Exception {
        assertEquals(tomorrowTimeDate, TimeUtils.Companion.getDate(timeMillis, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeDate, TimeUtils.Companion.getDate(timeString, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeDate, TimeUtils.Companion.getDate(timeStringFormat, mFormat, 1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(tomorrowTimeDate, TimeUtils.Companion.getDate(timeDate, 1, TimeConstants.INSTANCE.getDAY()));
    }

    @Test
    public void getMillisByNow() throws Exception {
        assertEquals(System.currentTimeMillis() + TimeConstants.INSTANCE.getDAY(), TimeUtils.Companion.getMillisByNow(1, TimeConstants.INSTANCE.getDAY()), delta);
    }

    @Test
    public void getStringByNow() throws Exception {
        long tomorrowMillis = TimeUtils.string2Millis(TimeUtils.Companion.getStringByNow(1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(System.currentTimeMillis() + TimeConstants.INSTANCE.getDAY(), tomorrowMillis, delta);
        tomorrowMillis = TimeUtils.Companion.string2Millis(TimeUtils.Companion.getStringByNow(1, mFormat, TimeConstants.INSTANCE.getDAY()), mFormat);
        assertEquals(System.currentTimeMillis() + TimeConstants.INSTANCE.getDAY(), tomorrowMillis, delta);
    }

    @Test
    public void getDateByNow() throws Exception {
        long tomorrowMillis = TimeUtils.Companion.date2Millis(TimeUtils.Companion.getDateByNow(1, TimeConstants.INSTANCE.getDAY()));
        assertEquals(System.currentTimeMillis() + TimeConstants.INSTANCE.getDAY(), TimeUtils.Companion.getMillisByNow(1, TimeConstants.INSTANCE.getDAY()), delta);
    }

    @Test
    public void isToday() throws Exception {
        long todayTimeMillis = System.currentTimeMillis();
        String todayTimeString = TimeUtils.millis2String(todayTimeMillis);
        String todayTimeStringFormat = TimeUtils.Companion.millis2String(todayTimeMillis, mFormat);
        Date todayTimeDate = TimeUtils.Companion.millis2Date(todayTimeMillis);
        long tomorrowTimeMillis = todayTimeMillis + TimeConstants.INSTANCE.getDAY();
        String tomorrowTimeString = TimeUtils.millis2String(tomorrowTimeMillis);
        Date tomorrowTimeDate = TimeUtils.Companion.millis2Date(tomorrowTimeMillis);
        assertTrue(TimeUtils.Companion.isToday(todayTimeString));
        assertTrue(TimeUtils.Companion.isToday(todayTimeStringFormat, mFormat));
        assertTrue(TimeUtils.Companion.isToday(todayTimeDate));
        assertTrue(TimeUtils.Companion.isToday(todayTimeMillis));
        assertFalse(TimeUtils.Companion.isToday(tomorrowTimeString));
        assertFalse(TimeUtils.Companion.isToday(tomorrowTimeStringFormat, mFormat));
        assertFalse(TimeUtils.Companion.isToday(tomorrowTimeDate));
        assertFalse(TimeUtils.Companion.isToday(tomorrowTimeMillis));
    }

    @Test
    public void isLeapYear() throws Exception {
        assertFalse(TimeUtils.Companion.isLeapYear(timeString));
        assertFalse(TimeUtils.Companion.isLeapYear(timeStringFormat, mFormat));
        assertFalse(TimeUtils.Companion.isLeapYear(timeDate));
        assertFalse(TimeUtils.Companion.isLeapYear(timeMillis));
        assertTrue(TimeUtils.Companion.isLeapYear(2016));
        assertFalse(TimeUtils.Companion.isLeapYear(2017));
    }

    @Test
    public void getChineseWeek() throws Exception {
        assertEquals("星期四", TimeUtils.Companion.getChineseWeek(timeString));
        assertEquals("星期四", TimeUtils.Companion.getChineseWeek(timeStringFormat, mFormat));
        assertEquals("星期四", TimeUtils.Companion.getChineseWeek(timeDate));
        assertEquals("星期四", TimeUtils.Companion.getChineseWeek(timeMillis));
    }

    @Test
    public void getUSWeek() throws Exception {
        assertEquals("Thursday", TimeUtils.Companion.getUSWeek(timeString));
        assertEquals("Thursday", TimeUtils.Companion.getUSWeek(timeStringFormat, mFormat));
        assertEquals("Thursday", TimeUtils.Companion.getUSWeek(timeDate));
        assertEquals("Thursday", TimeUtils.Companion.getUSWeek(timeMillis));
    }

    @Test
    public void getWeekIndex() throws Exception {
        assertEquals(5, TimeUtils.Companion.getWeekIndex(timeString));
        assertEquals(5, TimeUtils.Companion.getWeekIndex(timeStringFormat, mFormat));
        assertEquals(5, TimeUtils.Companion.getWeekIndex(timeDate));
        assertEquals(5, TimeUtils.Companion.getWeekIndex(timeMillis));
    }

    @Test
    public void getWeekOfMonth() throws Exception {
        assertEquals(1, TimeUtils.Companion.getWeekOfMonth(timeString));
        assertEquals(1, TimeUtils.Companion.getWeekOfMonth(timeStringFormat, mFormat));
        assertEquals(1, TimeUtils.Companion.getWeekOfMonth(timeDate));
        assertEquals(1, TimeUtils.Companion.getWeekOfMonth(timeMillis));
    }

    @Test
    public void getWeekOfYear() throws Exception {
        assertEquals(18, TimeUtils.Companion.getWeekOfYear(timeString));
        assertEquals(18, TimeUtils.Companion.getWeekOfYear(timeStringFormat, mFormat));
        assertEquals(18, TimeUtils.Companion.getWeekOfYear(timeDate));
        assertEquals(18, TimeUtils.Companion.getWeekOfYear(timeMillis));
    }

    @Test
    public void getChineseZodiac() throws Exception {
        assertEquals("鸡", TimeUtils.Companion.getChineseZodiac(timeString));
        assertEquals("鸡", TimeUtils.Companion.getChineseZodiac(timeStringFormat, mFormat));
        assertEquals("鸡", TimeUtils.Companion.getChineseZodiac(timeDate));
        assertEquals("鸡", TimeUtils.Companion.getChineseZodiac(timeMillis));
        assertEquals("鸡", TimeUtils.Companion.getChineseZodiac(2017));
    }

    @Test
    public void getZodiac() throws Exception {
        assertEquals("金牛座", TimeUtils.Companion.getZodiac(timeString));
        assertEquals("金牛座", TimeUtils.Companion.getZodiac(timeStringFormat, mFormat));
        assertEquals("金牛座", TimeUtils.Companion.getZodiac(timeDate));
        assertEquals("金牛座", TimeUtils.Companion.getZodiac(timeMillis));
        assertEquals("狮子座", TimeUtils.Companion.getZodiac(8, 16));
    }
}