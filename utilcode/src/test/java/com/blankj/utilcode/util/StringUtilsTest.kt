package com.blankj.utilcode.util

import org.junit.Assert.*
import org.junit.Test

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/16
 * desc  : StringUtils 单元测试
</pre> *
 */
class StringUtilsTest {

    @Test
    @Throws(Exception::class)
    fun isEmpty() {
        assertTrue(StringUtils.isEmpty(""))
        assertTrue(StringUtils.isEmpty(null))
        assertFalse(StringUtils.isEmpty(" "))
    }

    @Test
    @Throws(Exception::class)
    fun isSpace() {
        assertTrue(StringUtils.isSpace(""))
        assertTrue(StringUtils.isSpace(null))
        assertTrue(StringUtils.isSpace(" "))
        assertTrue(StringUtils.isSpace("　 \n\t\r"))
    }

    @Test
    @Throws(Exception::class)
    fun equals() {
        assertTrue(StringUtils.equals(null, null))
        assertTrue(StringUtils.equals("blankj", "blankj"))
        assertFalse(StringUtils.equals("blankj", "Blankj"))
    }

    @Test
    @Throws(Exception::class)
    fun equalsIgnoreCase() {
        assertTrue(StringUtils.equalsIgnoreCase(null, null))
        assertFalse(StringUtils.equalsIgnoreCase(null, "blankj"))
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "Blankj"))
        assertTrue(StringUtils.equalsIgnoreCase("blankj", "blankj"))
        assertFalse(StringUtils.equalsIgnoreCase("blankj", "blank"))
    }

    @Test
    @Throws(Exception::class)
    fun null2Length0() {
        assertEquals("", StringUtils.null2Length0(null))
    }

    @Test
    @Throws(Exception::class)
    fun length() {
        assertEquals(0, StringUtils.length(null).toLong())
        assertEquals(0, StringUtils.length("").toLong())
        assertEquals(6, StringUtils.length("blankj").toLong())
    }

    @Test
    @Throws(Exception::class)
    fun upperFirstLetter() {
        assertEquals("Blankj", StringUtils.upperFirstLetter("blankj"))
        assertEquals("Blankj", StringUtils.upperFirstLetter("Blankj"))
        assertEquals("1Blankj", StringUtils.upperFirstLetter("1Blankj"))
    }

    @Test
    @Throws(Exception::class)
    fun lowerFirstLetter() {
        assertEquals("blankj", StringUtils.lowerFirstLetter("blankj"))
        assertEquals("blankj", StringUtils.lowerFirstLetter("Blankj"))
        assertEquals("1blankj", StringUtils.lowerFirstLetter("1blankj"))
    }

    @Test
    @Throws(Exception::class)
    fun reverse() {
        assertEquals("jknalb", StringUtils.reverse("blankj"))
        assertEquals("knalb", StringUtils.reverse("blank"))
        assertEquals("文中试测", StringUtils.reverse("测试中文"))
        assertNull(StringUtils.reverse(null!!))
    }

    @Test
    @Throws(Exception::class)
    fun toDBC() {
        assertEquals(" ,.&", StringUtils.toDBC("　，．＆"))
    }

    @Test
    @Throws(Exception::class)
    fun toSBC() {
        assertEquals("　，．＆", StringUtils.toSBC(" ,.&"))
    }
}