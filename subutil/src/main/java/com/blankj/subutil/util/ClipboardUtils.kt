package com.blankj.subutil.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/25
 * desc  : 剪贴板相关工具类
</pre> *
 */
object ClipboardUtils {

    /**
     * 复制文本到剪贴板
     * 或
     * 获取剪贴板的文本
     *
     * @return 剪贴板的文本
     */
    var text: CharSequence?
        get() {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = clipboard.primaryClip
            return if (clip != null && clip.itemCount > 0) {
                clip.getItemAt(0).coerceToText(Utils.app)
            } else null
        }
        set(text) {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newPlainText("text", text)
        }

    /**
     * 复制uri到剪贴板
     * 或
     * 获取剪贴板的uri
     *
     * @return 剪贴板的uri
     */
    var uri: Uri?
        get() {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = clipboard.primaryClip
            return if (clip != null && clip.itemCount > 0) {
                clip.getItemAt(0).uri
            } else null
        }
        set(uri) {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newUri(Utils.app.contentResolver, "uri", uri)
        }


    /**
     * 复制意图到剪贴板
     * 或
     * 获取剪贴板的意图
     *
     * @return 剪贴板的意图
     */
    var intent: Intent?
        get() {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = clipboard.primaryClip
            return if (clip != null && clip.itemCount > 0) {
                clip.getItemAt(0).intent
            } else null
        }
        set(intent) {
            val clipboard = Utils.app.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newIntent("intent", intent)
        }
}
