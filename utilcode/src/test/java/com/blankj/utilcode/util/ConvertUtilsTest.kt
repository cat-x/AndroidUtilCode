package com.blankj.utilcode.util


import com.blankj.utilcode.constant.MemoryConstants
import com.blankj.utilcode.constant.TimeConstants
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/13
 * desc  : ConvertUtils 单元测试
</pre> *
 */
class ConvertUtilsTest {

    private val mBytes = byteArrayOf(0x00, 0x08, 0xdb.toByte(), 0x33, 0x45, 0xab.toByte(), 0x02, 0x23)
    private val hexString = "0008DB3345AB0223"

    private val mChars1 = charArrayOf('0', '1', '2')
    private val mBytes1 = byteArrayOf(48, 49, 50)

    @Test
    @Throws(Exception::class)
    fun bytes2HexString() {
        assertEquals(
                hexString,
                ConvertUtils.bytes2HexString(mBytes)
        )
    }

    @Test
    @Throws(Exception::class)
    fun hexString2Bytes() {
        assertTrue(
                Arrays.equals(
                        mBytes,
                        ConvertUtils.hexString2Bytes(hexString)
                )
        )
    }

    @Test
    @Throws(Exception::class)
    fun chars2Bytes() {
        assertTrue(
                Arrays.equals(
                        mBytes1,
                        ConvertUtils.chars2Bytes(mChars1)
                )
        )
    }

    @Test
    @Throws(Exception::class)
    fun bytes2Chars() {
        assertTrue(
                Arrays.equals(
                        mChars1,
                        ConvertUtils.bytes2Chars(mBytes1)
                )
        )
    }

    @Test
    @Throws(Exception::class)
    fun byte2MemorySize() {
        assertEquals(
                1024.0,
                ConvertUtils.byte2MemorySize(MemoryConstants.GB.toLong(), MemoryConstants.MB),
                0.001
        )
    }

    @Test
    @Throws(Exception::class)
    fun byte2FitMemorySize() {
        assertEquals(
                "3.098MB",
                ConvertUtils.byte2FitMemorySize((1024 * 1024 * 3 + 1024 * 100).toLong())
        )
    }

    @Test
    @Throws(Exception::class)
    fun millis2FitTimeSpan() {
        val millis = (6 * TimeConstants.DAY
                + 6 * TimeConstants.HOUR
                + 6 * TimeConstants.MIN
                + 6 * TimeConstants.SEC
                + 6).toLong()
        assertEquals(
                "6天6小时6分钟6秒6毫秒",
                ConvertUtils.millis2FitTimeSpan(millis, 7)
        )
        assertEquals(
                "6天6小时6分钟6秒",
                ConvertUtils.millis2FitTimeSpan(millis, 4)
        )
        assertEquals(
                "6天6小时6分钟",
                ConvertUtils.millis2FitTimeSpan(millis, 3)
        )
        assertEquals(
                "25天24分钟24秒24毫秒",
                ConvertUtils.millis2FitTimeSpan(millis * 4, 5)
        )
    }

    @Test
    @Throws(Exception::class)
    fun bytes2Bits_bits2Bytes() {
        assertEquals(
                "0111111111111010",
                ConvertUtils.bytes2Bits(byteArrayOf(0x7F, 0xFA.toByte()))
        )
        assertEquals(
                "0111111111111010",
                ConvertUtils.bytes2Bits(ConvertUtils.bits2Bytes("111111111111010"))
        )
    }

    @Test
    @Throws(Exception::class)
    fun inputStream2Bytes_bytes2InputStream() {
        val string = "this is test string"
        assertTrue(
                Arrays.equals(
                        string.toByteArray(charset("UTF-8")),
                        ConvertUtils.inputStream2Bytes(ConvertUtils.bytes2InputStream(string.toByteArray(charset("UTF-8"))))
                )
        )
    }

    @Test
    @Throws(Exception::class)
    fun inputStream2String_string2InputStream() {
        val string = "this is test string"
        assertEquals(
                string,
                ConvertUtils.inputStream2String(ConvertUtils.string2InputStream(string, "UTF-8"), "UTF-8")
        )
    }
}
