package com.blankj.utilcode.util

import android.support.v4.util.LongSparseArray
import android.support.v4.util.SimpleArrayMap
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import android.util.SparseLongArray
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/12/24
 * desc  : ObjectUtils 单元测试
</pre> *
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [23])
class ObjectUtilsTest {

    @Test
    @Throws(Exception::class)
    fun isEmpty() {
        val sb = StringBuilder("")
        val sb1 = StringBuilder(" ")
        val string = ""
        val string1 = " "
        val arr = arrayOf<IntArray>()
        val list = LinkedList<Int>()
        val map = HashMap<String, Int>()
        val sam = SimpleArrayMap<String, Int>()
        val sa = SparseArray<String>()
        val sba = SparseBooleanArray()
        val sia = SparseIntArray()
        val sla = SparseLongArray()
        val lsa = LongSparseArray<String>()
        val lsaV4 = android.util.LongSparseArray<String>()

        assertTrue(ObjectUtils.isEmpty(sb))
        assertFalse(ObjectUtils.isEmpty(sb1))
        assertTrue(ObjectUtils.isEmpty(string))
        assertFalse(ObjectUtils.isEmpty(string1))
        assertTrue(ObjectUtils.isEmpty(arr))
        assertTrue(ObjectUtils.isEmpty(list))
        assertTrue(ObjectUtils.isEmpty(map))
        assertTrue(ObjectUtils.isEmpty(sam))
        assertTrue(ObjectUtils.isEmpty(sa))
        assertTrue(ObjectUtils.isEmpty(sba))
        assertTrue(ObjectUtils.isEmpty(sia))
        assertTrue(ObjectUtils.isEmpty(sla))
        assertTrue(ObjectUtils.isEmpty(lsa))
        assertTrue(ObjectUtils.isEmpty(lsaV4))

        assertTrue(!ObjectUtils.isNotEmpty(sb))
        assertFalse(!ObjectUtils.isNotEmpty(sb1))
        assertTrue(!ObjectUtils.isNotEmpty(string))
        assertFalse(!ObjectUtils.isNotEmpty(string1))
        assertTrue(!ObjectUtils.isNotEmpty(arr))
        assertTrue(!ObjectUtils.isNotEmpty(list))
        assertTrue(!ObjectUtils.isNotEmpty(map))
        assertTrue(!ObjectUtils.isNotEmpty(sam))
        assertTrue(!ObjectUtils.isNotEmpty(sa))
        assertTrue(!ObjectUtils.isNotEmpty(sba))
        assertTrue(!ObjectUtils.isNotEmpty(sia))
        assertTrue(!ObjectUtils.isNotEmpty(sla))
        assertTrue(!ObjectUtils.isNotEmpty(lsa))
        assertTrue(!ObjectUtils.isNotEmpty(lsaV4))
    }

    @Test
    @Throws(Exception::class)
    fun equals() {
        assertTrue(ObjectUtils.equals(1, 1))
        assertTrue(ObjectUtils.equals("str", "str"))
        assertTrue(ObjectUtils.equals(null, null!!))

        assertFalse(ObjectUtils.equals(null, 1))
        assertFalse(ObjectUtils.equals(null, ""))
    }

}