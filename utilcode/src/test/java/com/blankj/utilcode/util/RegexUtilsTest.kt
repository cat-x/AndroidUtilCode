package com.blankj.utilcode.util

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/16
 * desc  : RegexUtils 单元测试
</pre> *
 */
class RegexUtilsTest {

    @Test
    @Throws(Exception::class)
    fun isMobileSimple() {
        assertTrue(RegexUtils.isMobileSimple("11111111111"))
    }

    @Test
    @Throws(Exception::class)
    fun isMobileExact() {
        assertFalse(RegexUtils.isMobileExact("11111111111"))
        assertTrue(RegexUtils.isMobileExact("13888880000"))
    }

    @Test
    @Throws(Exception::class)
    fun isTel() {
        assertTrue(RegexUtils.isTel("033-88888888"))
        assertTrue(RegexUtils.isTel("033-7777777"))
        assertTrue(RegexUtils.isTel("0444-88888888"))
        assertTrue(RegexUtils.isTel("0444-7777777"))
        assertTrue(RegexUtils.isTel("033 88888888"))
        assertTrue(RegexUtils.isTel("033 7777777"))
        assertTrue(RegexUtils.isTel("0444 88888888"))
        assertTrue(RegexUtils.isTel("0444 7777777"))
        assertTrue(RegexUtils.isTel("03388888888"))
        assertTrue(RegexUtils.isTel("0337777777"))
        assertTrue(RegexUtils.isTel("044488888888"))
        assertTrue(RegexUtils.isTel("04447777777"))

        assertFalse(RegexUtils.isTel("133-88888888"))
        assertFalse(RegexUtils.isTel("033-666666"))
        assertFalse(RegexUtils.isTel("0444-999999999"))
    }

    @Test
    @Throws(Exception::class)
    fun isIDCard18() {
        assertTrue(RegexUtils.isIDCard18("33698418400112523x"))
        assertTrue(RegexUtils.isIDCard18("336984184001125233"))
        assertFalse(RegexUtils.isIDCard18("336984184021125233"))
    }

    @Test
    @Throws(Exception::class)
    fun isEmail() {
        assertTrue(RegexUtils.isEmail("blankj@qq.com"))
        assertFalse(RegexUtils.isEmail("blankj@qq"))
    }

    @Test
    @Throws(Exception::class)
    fun isURL() {
        assertTrue(RegexUtils.isURL("http://blankj.com"))
        assertFalse(RegexUtils.isURL("https:blank"))
    }

    @Test
    @Throws(Exception::class)
    fun isZh() {
        assertTrue(RegexUtils.isZh("我"))
        assertFalse(RegexUtils.isZh("wo"))
    }

    @Test
    @Throws(Exception::class)
    fun isUsername() {
        assertTrue(RegexUtils.isUsername("小明233333"))
        assertFalse(RegexUtils.isUsername("小明"))
        assertFalse(RegexUtils.isUsername("小明233333_"))
    }

    @Test
    @Throws(Exception::class)
    fun isDate() {
        assertTrue(RegexUtils.isDate("2016-08-16"))
        assertTrue(RegexUtils.isDate("2016-02-29"))
        assertFalse(RegexUtils.isDate("2015-02-29"))
        assertFalse(RegexUtils.isDate("2016-8-16"))
    }

    @Test
    @Throws(Exception::class)
    fun isIP() {
        assertTrue(RegexUtils.isIP("255.255.255.0"))
        assertFalse(RegexUtils.isIP("256.255.255.0"))
    }

    @Test
    @Throws(Exception::class)
    fun isMatch() {
        assertTrue(RegexUtils.isMatch("\\d?", "1"))
        assertFalse(RegexUtils.isMatch("\\d?", "a"))
    }

    @Test
    @Throws(Exception::class)
    fun getMatches() {
        // 贪婪
        println(RegexUtils.getMatches("b.*j", "blankj blankj"))
        // 懒惰
        println(RegexUtils.getMatches("b.*?j", "blankj blankj"))
    }

    @Test
    @Throws(Exception::class)
    fun getSplits() {
        println(Arrays.asList(*RegexUtils.getSplits("1 2 3", " ")!!))
    }

    @Test
    @Throws(Exception::class)
    fun getReplaceFirst() {
        println(RegexUtils.getReplaceFirst("1 2 3", " ", ", "))
    }

    @Test
    @Throws(Exception::class)
    fun getReplaceAll() {
        println(RegexUtils.getReplaceAll("1 2 3", " ", ", "))
    }
}