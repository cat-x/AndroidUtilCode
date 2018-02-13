package com.blankj.utilcode.util

import org.junit.Assert.assertEquals
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
 * time  : 2016/08/12
 * desc  : EncodeUtils 单元测试
</pre> *
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class EncodeUtilsTest {

    @Test
    @Throws(Exception::class)
    fun urlEncode_urlDecode() {
        val urlEncodeString = "%E5%93%88%E5%93%88%E5%93%88"
        assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈"))
        assertEquals(urlEncodeString, EncodeUtils.urlEncode("哈哈哈", "UTF-8"))

        assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString))
        assertEquals("哈哈哈", EncodeUtils.urlDecode(urlEncodeString, "UTF-8"))
    }

    @Test
    @Throws(Exception::class)
    fun base64Decode_base64Encode() {
        assertTrue(
                Arrays.equals(
                        "blankj".toByteArray(),
                        EncodeUtils.base64Decode(EncodeUtils.base64Encode("blankj"))
                )
        )
        assertTrue(
                Arrays.equals(
                        "blankj".toByteArray(),
                        EncodeUtils.base64Decode(EncodeUtils.base64Encode2String("blankj".toByteArray()))
                )
        )
        assertEquals(
                "Ymxhbmtq",
                EncodeUtils.base64Encode2String("blankj".toByteArray())
        )
        assertTrue(
                Arrays.equals(
                        "Ymxhbmtq".toByteArray(),
                        EncodeUtils.base64Encode("blankj".toByteArray())
                )
        )
    }

    @Test
    @Throws(Exception::class)
    fun htmlEncode_htmlDecode() {
        val html = "<html>" +
                "<head>" +
                "<title>我的第一个 HTML 页面</title>" +
                "</head>" +
                "<body>" +
                "<p>body 元素的内容会显示在浏览器中。</p>" +
                "<p>title 元素的内容会显示在浏览器的标题栏中。</p>" +
                "</body>" +
                "</html>"
        val encodeHtml = "&lt;html&gt;&lt;head&gt;&lt;title&gt;我的第一个 HTML 页面&lt;/title&gt;&lt;/head&gt;&lt;body&gt;&lt;p&gt;body 元素的内容会显示在浏览器中。&lt;/p&gt;&lt;p&gt;title 元素的内容会显示在浏览器的标题栏中。&lt;/p&gt;&lt;/body&gt;&lt;/html&gt;"

        assertEquals(encodeHtml, EncodeUtils.htmlEncode(html))

        assertEquals(html, EncodeUtils.htmlDecode(encodeHtml).toString())
    }
}