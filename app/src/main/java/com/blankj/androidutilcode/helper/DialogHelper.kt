package com.blankj.androidutilcode.helper

import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.blankj.androidutilcode.R
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.OnRationaleListener.ShouldRequest

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/01/10
 * desc  : 对话框工具类
</pre> *
 */
object DialogHelper {

    fun showRationaleDialog(shouldRequest: ShouldRequest) {
        val topActivity = ActivityUtils.topActivity ?: return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> shouldRequest.again(true) }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> shouldRequest.again(false) }
                .setCancelable(false)
                .create()
                .show()

    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.topActivity ?: return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok) { dialog, which -> PermissionUtils.openAppSettings() }
                .setNegativeButton(android.R.string.cancel) { dialog, which -> }
                .setCancelable(false)
                .create()
                .show()
    }

    fun showKeyboardDialog() {
        val topActivity = ActivityUtils.topActivity ?: return
        val dialogView = LayoutInflater.from(topActivity).inflate(R.layout.dialog_keyboard, null)
        val etInput = dialogView.findViewById<EditText>(R.id.et_input)
        val dialog = AlertDialog.Builder(topActivity).setView(dialogView).create()
        dialog.setCanceledOnTouchOutside(false)
        val listener = View.OnClickListener { v ->
            when (v.id) {
                R.id.btn_hide_soft_input -> KeyboardUtils.hideSoftInput(etInput)
                R.id.btn_show_soft_input -> KeyboardUtils.showSoftInput(etInput)
                R.id.btn_toggle_soft_input -> KeyboardUtils.toggleSoftInput()
                R.id.btn_close_dialog -> {
                    KeyboardUtils.hideSoftInput(etInput)
                    dialog.dismiss()
                }
            }
        }
        dialogView.findViewById<View>(R.id.btn_hide_soft_input).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.btn_show_soft_input).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.btn_toggle_soft_input).setOnClickListener(listener)
        dialogView.findViewById<View>(R.id.btn_close_dialog).setOnClickListener(listener)
        dialog.show()
    }
}
