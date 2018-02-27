package com.blankj.androidutilcode.feature.sub.brightness

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ToggleButton
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseActivity
import com.blankj.subutil.util.BrightnessUtils
import com.blankj.subutil.util.Utils
import com.blankj.subutil.util.brightness
import com.blankj.utilcode.util.SpanUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2018/02/08
 * desc  :
</pre> *
 */
class BrightnessActivity : BaseActivity() {

    internal lateinit var tvBrightness: TextView
    internal lateinit var sbBrightness: SeekBar
    internal lateinit var tvWindowBrightness: TextView
    internal lateinit var sbWindowBrightness: SeekBar
    internal lateinit var tbAutoBrightness: ToggleButton

    private val brightnessChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            BrightnessUtils.brightness = progress
            updateBrightness()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    private val windowBrightnessChangeListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            window.brightness = progress
            updateWindowBrightness()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {

        }
    }

    override fun initData(bundle: Bundle?) {

    }

    override fun bindLayout(): Int {
        return R.layout.activity_brightness
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView(savedInstanceState: Bundle?, view: View?) {
        tvBrightness = findViewById(R.id.tv_brightness)
        sbBrightness = findViewById(R.id.sb_brightness)
        tvWindowBrightness = findViewById(R.id.tv_window_brightness)
        sbWindowBrightness = findViewById(R.id.sb_window_brightness)
        tbAutoBrightness = findViewById(R.id.tb_set_auto_brightness_enable)

        sbBrightness.progress = BrightnessUtils.brightness
        sbBrightness.setOnSeekBarChangeListener(brightnessChangeListener)
        updateBrightness()

        sbWindowBrightness.progress = window.brightness
        sbWindowBrightness.setOnSeekBarChangeListener(windowBrightnessChangeListener)
        updateWindowBrightness()

        tbAutoBrightness.isChecked = BrightnessUtils.isAutoBrightnessEnabled
        tbAutoBrightness.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(Utils.app)) {
                val intent = Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + Utils.app.packageName)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                Utils.app.startActivity(intent)
            }
        }

        tbAutoBrightness.setOnCheckedChangeListener { buttonView, isChecked ->
            val isSuccess = BrightnessUtils.setAutoBrightnessEnabled(isChecked)
            if (!isSuccess) {
                tbAutoBrightness.toggle()
            }
        }
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {

    }

    private fun updateBrightness() {
        tvBrightness.text = SpanUtils()
                .append(BrightnessUtils.brightness.toString())
                .create()
    }

    private fun updateWindowBrightness() {
        tvWindowBrightness.text = SpanUtils()
                .append(window.brightness.toString())
                .create()
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BrightnessActivity::class.java)
            context.startActivity(starter)
        }
    }
}
