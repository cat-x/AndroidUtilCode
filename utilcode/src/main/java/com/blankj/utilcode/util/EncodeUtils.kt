package com.blankj.utilcode.util

import android.os.Build
import android.text.Html
import android.util.Base64

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/08/07
 * desc  : 编码解码相关工具类
</pre> *
 */
class EncodeUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        /**
         * URL 编码
         *
         * 若系统不支持指定的编码字符集,则直接将 input 原样返回
         *
         * @param input   要编码的字符
         * @param charset 字符集
         * @return 编码为字符集的字符串
         */
        @JvmOverloads
        fun urlEncode(input: String, charset: String = "UTF-8"): String {
            try {
                return URLEncoder.encode(input, charset)
            } catch (e: UnsupportedEncodingException) {
                return input
            }

        }

        /**
         * URL 解码
         *
         * 若系统不支持指定的解码字符集,则直接将 input 原样返回
         *
         * @param input   要解码的字符串
         * @param charset 字符集
         * @return URL 解码为指定字符集的字符串
         */
        @JvmOverloads
        fun urlDecode(input: String, charset: String = "UTF-8"): String {
            try {
                return URLDecoder.decode(input, charset)
            } catch (e: UnsupportedEncodingException) {
                return input
            }

        }

        /**
         * Base64 编码
         *
         * @param input 要编码的字符串
         * @return Base64 编码后的字符串
         */
        fun base64Encode(input: String): ByteArray {
            return base64Encode(input.toByteArray())
        }

        /**
         * Base64 编码
         *
         * @param input 要编码的字节数组
         * @return Base64 编码后的字符串
         */
        fun base64Encode(input: ByteArray): ByteArray {
            return Base64.encode(input, Base64.NO_WRAP)
        }

        /**
         * Base64 编码
         *
         * @param input 要编码的字节数组
         * @return Base64 编码后的字符串
         */
        fun base64Encode2String(input: ByteArray): String {
            return Base64.encodeToString(input, Base64.NO_WRAP)
        }

        /**
         * Base64 解码
         *
         * @param input 要解码的字符串
         * @return Base64 解码后的字符串
         */
        fun base64Decode(input: String): ByteArray {
            return Base64.decode(input, Base64.NO_WRAP)
        }

        /**
         * Base64 解码
         *
         * @param input 要解码的字符串
         * @return Base64 解码后的字符串
         */
        fun base64Decode(input: ByteArray): ByteArray {
            return Base64.decode(input, Base64.NO_WRAP)
        }

        /**
         * Base64URL 安全编码
         *
         * 将 Base64 中的 URL 非法字符�?,/=转为其他字符, 见 RFC3548
         *
         * @param input 要 Base64URL 安全编码的字符串
         * @return Base64URL 安全编码后的字符串
         */
        fun base64UrlSafeEncode(input: String): ByteArray {
            return Base64.encode(input.toByteArray(), Base64.URL_SAFE)
        }

        /**
         * Html 编码
         *
         * @param input 要 Html 编码的字符串
         * @return Html 编码后的字符串
         */
        fun htmlEncode(input: CharSequence): String {
            val sb = StringBuilder()
            var c: Char
            var i = 0
            val len = input.length
            while (i < len) {
                c = input[i]
                when (c) {
                    '<' -> sb.append("&lt;") //$NON-NLS-1$
                    '>' -> sb.append("&gt;") //$NON-NLS-1$
                    '&' -> sb.append("&amp;") //$NON-NLS-1$
                    '\'' ->
                        //http://www.w3.org/TR/xhtml1
                        // The named character reference &apos; (the apostrophe, U+0027) was
                        // introduced in XML 1.0 but does not appear in HTML. Authors should
                        // therefore use &#39; instead of &apos; to work as expected in HTML 4
                        // user agents.
                        sb.append("&#39;") //$NON-NLS-1$
                    '"' -> sb.append("&quot;") //$NON-NLS-1$
                    else -> sb.append(c)
                }
                i++
            }
            return sb.toString()
        }

        /**
         * Html 解码
         *
         * @param input 待解码的字符串
         * @return Html 解码后的字符串
         */
        fun htmlDecode(input: String): CharSequence {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(input)
            }
        }
    }
}
/**
 * URL 编码
 *
 * 若想自己指定字符集,可以使用[.urlEncode]方法
 *
 * @param input 要编码的字符
 * @return 编码为 UTF-8 的字符串
 */
/**
 * URL 解码
 *
 * 若想自己指定字符集,可以使用 [.urlDecode]方法
 *
 * @param input 要解码的字符串
 * @return URL 解码后的字符串
 */
