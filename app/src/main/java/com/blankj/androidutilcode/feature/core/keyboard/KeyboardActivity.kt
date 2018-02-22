package com.blankj.androidutilcode.feature.core.keyboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseBackActivity
import com.blankj.androidutilcode.helper.DialogHelper
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/27
 * desc  : Keyboard 工具类 Demo
</pre> *
 */
class KeyboardActivity : BaseBackActivity() {

    internal lateinit var tvAboutKeyboard: TextView
    internal lateinit var etInput: EditText
    private val dialog: AlertDialog? = null

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_keyboard
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        toolBar!!.title = getString(R.string.demo_keyboard)

        etInput = findViewById(R.id.et_input)
        findViewById<View>(R.id.btn_hide_soft_input).setOnClickListener(this)
        findViewById<View>(R.id.btn_show_soft_input).setOnClickListener(this)
        findViewById<View>(R.id.btn_toggle_soft_input).setOnClickListener(this)
        findViewById<View>(R.id.btn_keyboard_in_fragment).setOnClickListener(this)
        tvAboutKeyboard = findViewById(R.id.tv_about_keyboard)

        KeyboardUtils.registerSoftInputChangedListener(this,
                object : KeyboardUtils.OnSoftInputChangedListener {
                    override fun onSoftInputChanged(height: Int) {
                        tvAboutKeyboard.text = SpanUtils()
                                .appendLine("isSoftInputVisible: " + KeyboardUtils.isSoftInputVisible(this@KeyboardActivity))
                                .append("height: " + height)
                                .create()
                    }
                })
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_hide_soft_input -> KeyboardUtils.hideSoftInput(this)
            R.id.btn_show_soft_input -> KeyboardUtils.showSoftInput(this)
            R.id.btn_toggle_soft_input -> KeyboardUtils.toggleSoftInput()
            R.id.btn_keyboard_in_fragment -> {
                DialogHelper.showKeyboardDialog()
                KeyboardUtils.showSoftInput(this)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        ?: return super.dispatchTouchEvent(ev)
                imm.hideSoftInputFromWindow(v!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        return false
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, KeyboardActivity::class.java)
            context.startActivity(starter)
        }
    }
}
