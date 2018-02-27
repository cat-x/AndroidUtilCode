package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.blankj.utilcode.constant.MemoryConstants
import com.blankj.utilcode.constant.TimeConstants
import java.io.*
import kotlin.experimental.or

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/13
 * desc  : 转换相关工具类
</pre> *
 */
object ConvertUtils {


    /**
     * outputStream 转 inputStream
     *
     * @param out 输出流
     * @return inputStream 子类
     */
    fun output2InputStream(out: OutputStream?): ByteArrayInputStream? {
        return if (out == null) null else ByteArrayInputStream((out as ByteArrayOutputStream).toByteArray())
    }


    private val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * byteArr 转 hexString
     *
     * 例如：
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16 进制大写字符串
     */
    fun bytes2HexString(bytes: ByteArray?): String? {
        if (bytes == null) return null
        val len = bytes.size
        if (len <= 0) return null
        val ret = CharArray(len shl 1)
        var i = 0
        var j = 0
        while (i < len) {
            ret[j++] = hexDigits[bytes[i].toInt().ushr(4) and 0x0f]
            ret[j++] = hexDigits[bytes[i].toInt() and 0x0f]
            i++
        }
        return String(ret)
    }

    /**
     * hexString 转 byteArr
     *
     * 例如：
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    fun hexString2Bytes(hexString: String): ByteArray? {
        var string = hexString
        if (isSpace(string)) return null
        var len = string.length
        if (len % 2 != 0) {
            string = "0" + string
            len += 1
        }
        val hexBytes = string.toUpperCase().toCharArray()
        val ret = ByteArray(len shr 1)
        var i = 0
        while (i < len) {
            ret[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
            i += 2
        }
        return ret
    }

    /**
     * hexChar 转 int
     *
     * @param hexChar hex 单个字节
     * @return 0..15
     */
    private fun hex2Dec(hexChar: Char): Int {
        return if (hexChar in '0'..'9') {
            hexChar - '0'
        } else if (hexChar in 'A'..'F') {
            hexChar - 'A' + 10
        } else {
            throw IllegalArgumentException()
        }
    }

    /**
     * charArr 转 byteArr
     *
     * @param chars 字符数组
     * @return 字节数组
     */
    fun chars2Bytes(chars: CharArray?): ByteArray? {
        if (chars == null || chars.isEmpty()) return null
        val len = chars.size
        val bytes = ByteArray(len)
        for (i in 0 until len) {
            bytes[i] = chars[i].toByte()
        }
        return bytes
    }

    /**
     * byteArr 转 charArr
     *
     * @param bytes 字节数组
     * @return 字符数组
     */
    fun bytes2Chars(bytes: ByteArray?): CharArray? {
        if (bytes == null) return null
        val len = bytes.size
        if (len <= 0) return null
        val chars = CharArray(len)
        for (i in 0 until len) {
            chars[i] = (bytes[i].toInt() and 0xff).toChar()
        }
        return chars
    }

    /**
     * 以 unit 为单位的内存大小转字节数
     *
     * @param memorySize 大小
     * @param unit       单位类型
     *
     *  * [MemoryConstants.BYTE]: 字节
     *  * [MemoryConstants.KB]  : 千字节
     *  * [MemoryConstants.MB]  : 兆
     *  * [MemoryConstants.GB]  : GB
     *
     * @return 字节数
     */
    fun memorySize2Byte(memorySize: Long, @MemoryConstants.Unit unit: Int): Long {
        return if (memorySize < 0) -1 else memorySize * unit
    }

    /**
     * 字节数转以 unit 为单位的内存大小
     *
     * @param byteNum 字节数
     * @param unit    单位类型
     *
     *  * [MemoryConstants.BYTE]: 字节
     *  * [MemoryConstants.KB]  : 千字节
     *  * [MemoryConstants.MB]  : 兆
     *  * [MemoryConstants.GB]  : GB
     *
     * @return 以 unit 为单位的 size
     */
    fun byte2MemorySize(byteNum: Long, @MemoryConstants.Unit unit: Int): Double {
        return if (byteNum < 0) -1.0 else byteNum.toDouble() / unit
    }

    /**
     * 字节数转合适内存大小
     *
     * 保留 3 位小数
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    fun byte2FitMemorySize(byteNum: Long): String {
        return when {
            byteNum < 0 -> "shouldn't be less than zero!"
            byteNum < MemoryConstants.KB -> String.format("%.3fB", byteNum.toDouble())
            byteNum < MemoryConstants.MB -> String.format("%.3fKB", byteNum.toDouble() / MemoryConstants.KB)
            byteNum < MemoryConstants.GB -> String.format("%.3fMB", byteNum.toDouble() / MemoryConstants.MB)
            else -> String.format("%.3fGB", byteNum.toDouble() / MemoryConstants.GB)
        }
    }

    /**
     * 以 unit 为单位的时间长度转毫秒时间戳
     *
     * @param timeSpan 毫秒时间戳
     * @param unit     单位类型
     *
     *  * [TimeConstants.MSEC]: 毫秒
     *  * [TimeConstants.SEC]: 秒
     *  * [TimeConstants.MIN]: 分
     *  * [TimeConstants.HOUR]: 小时
     *  * [TimeConstants.DAY]: 天
     *
     * @return 毫秒时间戳
     */
    fun timeSpan2Millis(timeSpan: Long, @TimeConstants.Unit unit: Int): Long {
        return timeSpan * unit
    }

    /**
     * 毫秒时间戳转以 unit 为单位的时间长度
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *
     *  * [TimeConstants.MSEC]: 毫秒
     *  * [TimeConstants.SEC]: 秒
     *  * [TimeConstants.MIN]: 分
     *  * [TimeConstants.HOUR]: 小时
     *  * [TimeConstants.DAY]: 天
     *
     * @return 以 unit 为单位的时间长度
     */
    fun millis2TimeSpan(millis: Long, @TimeConstants.Unit unit: Int): Long {
        return millis / unit
    }

    /**
     * 毫秒时间戳转合适时间长度
     *
     * @param millis    毫秒时间戳
     *
     * 小于等于 0，返回 null
     * @param precision 精度
     *
     *  * precision = 0，返回 null
     *  * precision = 1，返回天
     *  * precision = 2，返回天和小时
     *  * precision = 3，返回天、小时和分钟
     *  * precision = 4，返回天、小时、分钟和秒
     *  * precision &gt;= 5，返回天、小时、分钟、秒和毫秒
     *
     * @return 合适时间长度
     */
    @SuppressLint("DefaultLocale")
    fun millis2FitTimeSpan(millis: Long, precision: Int): String? {
        var millis1 = millis
        var precision1 = precision
        if (millis1 <= 0 || precision1 <= 0) return null
        val sb = StringBuilder()
        val units = arrayOf("天", "小时", "分钟", "秒", "毫秒")
        val unitLen = intArrayOf(86400000, 3600000, 60000, 1000, 1)
        precision1 = Math.min(precision1, 5)
        for (i in 0 until precision1) {
            if (millis1 >= unitLen[i]) {
                val mode = millis1 / unitLen[i]
                millis1 -= mode * unitLen[i]
                sb.append(mode).append(units[i])
            }
        }
        return sb.toString()
    }

    /**
     * bytes 转 bits
     *
     * @param bytes 字节数组
     * @return bits
     */
    fun bytes2Bits(bytes: ByteArray): String {
        val sb = StringBuilder()
        for (aByte in bytes) {
            for (j in 7 downTo 0) {
                sb.append(if (aByte.toInt() shr j and 0x01 == 0) '0' else '1')
            }
        }
        return sb.toString()
    }

    /**
     * bits 转 bytes
     *
     * @param bits 二进制
     * @return bytes
     */
    fun bits2Bytes(bits: String): ByteArray {
        var bitsTemp = bits
        val lenMod = bitsTemp.length % 8
        var byteLen = bitsTemp.length / 8
        // 不是 8 的倍数前面补 0
        if (lenMod != 0) {
            for (i in lenMod..7) {
                bitsTemp = "0" + bitsTemp
            }
            byteLen++
        }
        val bytes = ByteArray(byteLen)
        for (i in 0 until byteLen) {
            for (j in 0..7) {
                bytes[i] = (bytes[i].toInt() shl 1).toByte()
                bytes[i] = bytes[i] or (bitsTemp[i * 8 + j] - '0').toByte()
            }
        }
        return bytes
    }

    /**
     * inputStream 转 outputStream
     *
     * @param `is` 输入流
     * @return outputStream 子类
     */
    fun input2OutputStream(inputStream: InputStream?): ByteArrayOutputStream? {
        if (inputStream == null) return null
        try {
            val os = ByteArrayOutputStream()
            val b = ByteArray(MemoryConstants.KB)
            var len: Int = -1
            while (({ len = inputStream.read(b, 0, MemoryConstants.KB);len }()) != -1) {
                os.write(b, 0, len)
            }
            return os
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            CloseUtils.closeIO(inputStream)
        }
    }

    /**
     * inputStream 转 byteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    fun inputStream2Bytes(`is`: InputStream?): ByteArray? {
        return if (`is` == null) null else input2OutputStream(`is`)!!.toByteArray()
    }

    /**
     * byteArr 转 inputStream
     *
     * @param bytes 字节数组
     * @return 输入流
     */
    fun bytes2InputStream(bytes: ByteArray?): InputStream? {
        return if (bytes == null || bytes.isEmpty()) null else ByteArrayInputStream(bytes)
    }

    /**
     * outputStream 转 byteArr
     *
     * @param out 输出流
     * @return 字节数组
     */
    fun outputStream2Bytes(out: OutputStream?): ByteArray? {
        return if (out == null) null else (out as ByteArrayOutputStream).toByteArray()
    }

    /**
     * outputStream 转 byteArr
     *
     * @param bytes 字节数组
     * @return 字节数组
     */
    fun bytes2OutputStream(bytes: ByteArray?): OutputStream? {
        if (bytes == null || bytes.isEmpty()) return null
        var os: ByteArrayOutputStream? = null
        try {
            os = ByteArrayOutputStream()
            os.write(bytes)
            return os
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            CloseUtils.closeIO(os)
        }
    }

    /**
     * inputStream 转 string 按编码
     *
     * @param inputStream          输入流
     * @param charsetName 编码格式
     * @return 字符串
     */
    fun inputStream2String(inputStream: InputStream?, charsetName: String): String? {
        if (inputStream == null || isSpace(charsetName)) return null
        try {
            return String(inputStream2Bytes(inputStream)!!, charset(charsetName))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * string 转 inputStream 按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    fun string2InputStream(string: String?, charsetName: String): InputStream? {
        if (string == null || isSpace(charsetName)) return null
        try {
            return ByteArrayInputStream(string.toByteArray(charset(charsetName)))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * outputStream 转 string 按编码
     *
     * @param out         输出流
     * @param charsetName 编码格式
     * @return 字符串
     */
    fun outputStream2String(out: OutputStream?, charsetName: String): String? {
        if (out == null || isSpace(charsetName)) return null
        try {
            return String(outputStream2Bytes(out)!!, charset(charsetName))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * string 转 outputStream 按编码
     *
     * @param string      字符串
     * @param charsetName 编码格式
     * @return 输入流
     */
    fun string2OutputStream(string: String?, charsetName: String): OutputStream? {
        if (string == null || isSpace(charsetName)) return null
        try {
            return bytes2OutputStream(string.toByteArray(charset(charsetName)))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * bitmap 转 byteArr
     *
     * @param bitmap bitmap 对象
     * @param format 格式
     * @return 字节数组
     */
    fun bitmap2Bytes(bitmap: Bitmap?, format: Bitmap.CompressFormat): ByteArray? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

    /**
     * byteArr 转 bitmap
     *
     * @param bytes 字节数组
     * @return bitmap
     */
    fun bytes2Bitmap(bytes: ByteArray?): Bitmap? {
        return if (bytes == null || bytes.isEmpty())
            null
        else
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable drawable 对象
     * @return bitmap
     */
    fun drawable2Bitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1,
                    if (drawable.opacity != PixelFormat.OPAQUE)
                        Bitmap.Config.ARGB_8888
                    else
                        Bitmap.Config.RGB_565)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE)
                        Bitmap.Config.ARGB_8888
                    else
                        Bitmap.Config.RGB_565)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * bitmap 转 drawable
     *
     * @param bitmap bitmap 对象
     * @return drawable
     */
    fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
        return if (bitmap == null) null else BitmapDrawable(Utils.app.resources, bitmap)
    }

    /**
     * drawable 转 byteArr
     *
     * @param drawable drawable 对象
     * @param format   格式
     * @return 字节数组
     */
    fun drawable2Bytes(drawable: Drawable?,
                       format: Bitmap.CompressFormat): ByteArray? {
        return if (drawable == null) null else bitmap2Bytes(drawable2Bitmap(drawable), format)
    }

    /**
     * byteArr 转 drawable
     *
     * @param bytes 字节数组
     * @return drawable
     */
    fun bytes2Drawable(bytes: ByteArray?): Drawable? {
        return if (bytes == null) null else bitmap2Drawable(bytes2Bitmap(bytes))
    }

    /**
     * view 转 Bitmap
     *
     * @param view 视图
     * @return bitmap
     */
    fun view2Bitmap(view: View?): Bitmap? {
        if (view == null) return null
        val ret = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(ret)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return ret
    }

    /**
     * dp 转 px
     *
     * @param dpValue dp 值
     * @return px 值
     */
    fun dp2px(dpValue: Float): Int {
        val scale = Utils.app.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * px 转 dp
     *
     * @param pxValue px 值
     * @return dp 值
     */
    fun px2dp(pxValue: Float): Int {
        val scale = Utils.app.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * sp 转 px
     *
     * @param spValue sp 值
     * @return px 值
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = Utils.app.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * px 转 sp
     *
     * @param pxValue px 值
     * @return sp 值
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = Utils.app.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 待校验字符串
     * @return `true`: null 或全空白字符<br></br> `false`: 不为 null 且不全空白字符
     */
    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }

}
