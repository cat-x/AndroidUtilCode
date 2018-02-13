package com.blankj.subutil.util

import android.content.Intent
import android.net.Uri
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/26
 * desc  : ClipboardUtils单元测试
</pre> *
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ClipboardUtilsTest {

    @Test
    @Throws(Exception::class)
    fun testText() {
        ClipboardUtils.copyText("test")
        assertEquals("test", ClipboardUtils.text)
    }

    @Test
    @Throws(Exception::class)
    fun testUri() {
        ClipboardUtils.copyUri(Uri.parse("http://www.blankj.com"))
        println(ClipboardUtils.uri)
    }

    @Test
    @Throws(Exception::class)
    fun testIntent() {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_DIAL
        ClipboardUtils.copyIntent(intent)
        println(ClipboardUtils.text)
    }

    companion object {

        init {
            TestUtils.init()
        }
    }
}