package com.blankj.utilcode.util;

import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/24
 *     desc  : ObjectUtils 单元测试
 * </pre>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 23)
public class ObjectUtilsTest {

    @Test
    public void isEmpty() throws Exception {
        StringBuilder sb = new StringBuilder("");
        StringBuilder sb1 = new StringBuilder(" ");
        String string = "";
        String string1 = " ";
        int[][] arr = new int[][]{};
        LinkedList<Integer> list = new LinkedList<>();
        HashMap<String, Integer> map = new HashMap<>();
        SimpleArrayMap<String, Integer> sam = new SimpleArrayMap<>();
        SparseArray<String> sa = new SparseArray<>();
        SparseBooleanArray sba = new SparseBooleanArray();
        SparseIntArray sia = new SparseIntArray();
        SparseLongArray sla = new SparseLongArray();
        LongSparseArray<String> lsa = new LongSparseArray<>();
        android.util.LongSparseArray<String> lsaV4 = new android.util.LongSparseArray<>();

        assertTrue(ObjectUtils.Companion.isEmpty(sb));
        assertFalse(ObjectUtils.Companion.isEmpty(sb1));
        assertTrue(ObjectUtils.Companion.isEmpty(string));
        assertFalse(ObjectUtils.Companion.isEmpty(string1));
        assertTrue(ObjectUtils.Companion.isEmpty(arr));
        assertTrue(ObjectUtils.Companion.isEmpty(list));
        assertTrue(ObjectUtils.Companion.isEmpty(map));
        assertTrue(ObjectUtils.Companion.isEmpty(sam));
        assertTrue(ObjectUtils.Companion.isEmpty(sa));
        assertTrue(ObjectUtils.Companion.isEmpty(sba));
        assertTrue(ObjectUtils.Companion.isEmpty(sia));
        assertTrue(ObjectUtils.Companion.isEmpty(sla));
        assertTrue(ObjectUtils.Companion.isEmpty(lsa));
        assertTrue(ObjectUtils.Companion.isEmpty(lsaV4));

        assertTrue(!ObjectUtils.Companion.isNotEmpty(sb));
        assertFalse(!ObjectUtils.Companion.isNotEmpty(sb1));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(string));
        assertFalse(!ObjectUtils.Companion.isNotEmpty(string1));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(arr));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(list));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(map));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(sam));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(sa));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(sba));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(sia));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(sla));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(lsa));
        assertTrue(!ObjectUtils.Companion.isNotEmpty(lsaV4));
    }

    @Test
    public void equals() throws Exception {
        assertTrue(ObjectUtils.Companion.equals(1, 1));
        assertTrue(ObjectUtils.Companion.equals("str", "str"));
        assertTrue(ObjectUtils.Companion.equals(null, null));

        assertFalse(ObjectUtils.Companion.equals(null, 1));
        assertFalse(ObjectUtils.Companion.equals(null, ""));
    }

}