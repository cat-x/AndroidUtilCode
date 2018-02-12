package com.blankj.utilcode.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : StringUtils 单元测试
 * </pre>
 */
public class StringUtilsTest {

    @Test
    public void isEmpty() throws Exception {
        assertTrue(StringUtils.Companion.isEmpty(""));
        assertTrue(StringUtils.Companion.isEmpty(null));
        assertFalse(StringUtils.Companion.isEmpty(" "));
    }

    @Test
    public void isSpace() throws Exception {
        assertTrue(StringUtils.Companion.isSpace(""));
        assertTrue(StringUtils.Companion.isSpace(null));
        assertTrue(StringUtils.Companion.isSpace(" "));
        assertTrue(StringUtils.Companion.isSpace("　 \n\t\r"));
    }

    @Test
    public void equals() throws Exception {
        assertTrue(StringUtils.Companion.equals(null, null));
        assertTrue(StringUtils.Companion.equals("blankj", "blankj"));
        assertFalse(StringUtils.Companion.equals("blankj", "Blankj"));
    }

    @Test
    public void equalsIgnoreCase() throws Exception {
        assertTrue(StringUtils.Companion.equalsIgnoreCase(null, null));
        assertFalse(StringUtils.Companion.equalsIgnoreCase(null, "blankj"));
        assertTrue(StringUtils.Companion.equalsIgnoreCase("blankj", "Blankj"));
        assertTrue(StringUtils.Companion.equalsIgnoreCase("blankj", "blankj"));
        assertFalse(StringUtils.Companion.equalsIgnoreCase("blankj", "blank"));
    }

    @Test
    public void null2Length0() throws Exception {
        assertEquals("", StringUtils.Companion.null2Length0(null));
    }

    @Test
    public void length() throws Exception {
        assertEquals(0, StringUtils.Companion.length(null));
        assertEquals(0, StringUtils.Companion.length(""));
        assertEquals(6, StringUtils.Companion.length("blankj"));
    }

    @Test
    public void upperFirstLetter() throws Exception {
        assertEquals("Blankj", StringUtils.Companion.upperFirstLetter("blankj"));
        assertEquals("Blankj", StringUtils.Companion.upperFirstLetter("Blankj"));
        assertEquals("1Blankj", StringUtils.Companion.upperFirstLetter("1Blankj"));
    }

    @Test
    public void lowerFirstLetter() throws Exception {
        assertEquals("blankj", StringUtils.Companion.lowerFirstLetter("blankj"));
        assertEquals("blankj", StringUtils.Companion.lowerFirstLetter("Blankj"));
        assertEquals("1blankj", StringUtils.Companion.lowerFirstLetter("1blankj"));
    }

    @Test
    public void reverse() throws Exception {
        assertEquals("jknalb", StringUtils.Companion.reverse("blankj"));
        assertEquals("knalb", StringUtils.Companion.reverse("blank"));
        assertEquals("文中试测", StringUtils.Companion.reverse("测试中文"));
        assertNull(StringUtils.Companion.reverse(null));
    }

    @Test
    public void toDBC() throws Exception {
        assertEquals(" ,.&", StringUtils.Companion.toDBC("　，．＆"));
    }

    @Test
    public void toSBC() throws Exception {
        assertEquals("　，．＆", StringUtils.Companion.toSBC(" ,.&"));
    }
}