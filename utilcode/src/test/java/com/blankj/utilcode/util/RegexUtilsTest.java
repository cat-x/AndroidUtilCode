package com.blankj.utilcode.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/08/16
 *     desc  : RegexUtils 单元测试
 * </pre>
 */
public class RegexUtilsTest {

    @Test
    public void isMobileSimple() throws Exception {
        assertTrue(RegexUtils.Companion.isMobileSimple("11111111111"));
    }

    @Test
    public void isMobileExact() throws Exception {
        assertFalse(RegexUtils.Companion.isMobileExact("11111111111"));
        assertTrue(RegexUtils.Companion.isMobileExact("13888880000"));
    }

    @Test
    public void isTel() throws Exception {
        assertTrue(RegexUtils.Companion.isTel("033-88888888"));
        assertTrue(RegexUtils.Companion.isTel("033-7777777"));
        assertTrue(RegexUtils.Companion.isTel("0444-88888888"));
        assertTrue(RegexUtils.Companion.isTel("0444-7777777"));
        assertTrue(RegexUtils.Companion.isTel("033 88888888"));
        assertTrue(RegexUtils.Companion.isTel("033 7777777"));
        assertTrue(RegexUtils.Companion.isTel("0444 88888888"));
        assertTrue(RegexUtils.Companion.isTel("0444 7777777"));
        assertTrue(RegexUtils.Companion.isTel("03388888888"));
        assertTrue(RegexUtils.Companion.isTel("0337777777"));
        assertTrue(RegexUtils.Companion.isTel("044488888888"));
        assertTrue(RegexUtils.Companion.isTel("04447777777"));

        assertFalse(RegexUtils.Companion.isTel("133-88888888"));
        assertFalse(RegexUtils.Companion.isTel("033-666666"));
        assertFalse(RegexUtils.Companion.isTel("0444-999999999"));
    }

    @Test
    public void isIDCard18() throws Exception {
        assertTrue(RegexUtils.Companion.isIDCard18("33698418400112523x"));
        assertTrue(RegexUtils.Companion.isIDCard18("336984184001125233"));
        assertFalse(RegexUtils.Companion.isIDCard18("336984184021125233"));
    }

    @Test
    public void isEmail() throws Exception {
        assertTrue(RegexUtils.Companion.isEmail("blankj@qq.com"));
        assertFalse(RegexUtils.Companion.isEmail("blankj@qq"));
    }

    @Test
    public void isURL() throws Exception {
        assertTrue(RegexUtils.Companion.isURL("http://blankj.com"));
        assertFalse(RegexUtils.Companion.isURL("https:blank"));
    }

    @Test
    public void isZh() throws Exception {
        assertTrue(RegexUtils.Companion.isZh("我"));
        assertFalse(RegexUtils.Companion.isZh("wo"));
    }

    @Test
    public void isUsername() throws Exception {
        assertTrue(RegexUtils.Companion.isUsername("小明233333"));
        assertFalse(RegexUtils.Companion.isUsername("小明"));
        assertFalse(RegexUtils.Companion.isUsername("小明233333_"));
    }

    @Test
    public void isDate() throws Exception {
        assertTrue(RegexUtils.Companion.isDate("2016-08-16"));
        assertTrue(RegexUtils.Companion.isDate("2016-02-29"));
        assertFalse(RegexUtils.Companion.isDate("2015-02-29"));
        assertFalse(RegexUtils.Companion.isDate("2016-8-16"));
    }

    @Test
    public void isIP() throws Exception {
        assertTrue(RegexUtils.Companion.isIP("255.255.255.0"));
        assertFalse(RegexUtils.Companion.isIP("256.255.255.0"));
    }

    @Test
    public void isMatch() throws Exception {
        assertTrue(RegexUtils.Companion.isMatch("\\d?", "1"));
        assertFalse(RegexUtils.Companion.isMatch("\\d?", "a"));
    }

    @Test
    public void getMatches() throws Exception {
        // 贪婪
        System.out.println(RegexUtils.Companion.getMatches("b.*j", "blankj blankj"));
        // 懒惰
        System.out.println(RegexUtils.Companion.getMatches("b.*?j", "blankj blankj"));
    }

    @Test
    public void getSplits() throws Exception {
        System.out.println(Arrays.asList(RegexUtils.Companion.getSplits("1 2 3", " ")));
    }

    @Test
    public void getReplaceFirst() throws Exception {
        System.out.println(RegexUtils.Companion.getReplaceFirst("1 2 3", " ", ", "));
    }

    @Test
    public void getReplaceAll() throws Exception {
        System.out.println(RegexUtils.Companion.getReplaceAll("1 2 3", " ", ", "));
    }
}