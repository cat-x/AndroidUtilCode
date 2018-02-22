package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.UtilsApp
import com.blankj.androidutilcode.base.BaseActivity
import com.blankj.utilcode.util.BarUtils
import java.util.*


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : Bar 工具类 Demo
</pre> *
 */
class BarStatusColorActivity : BaseActivity() {

    private var mRandom: Random? = null
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private var mTvStatusAlpha: TextView? = null
    private var sbChangeAlpha: SeekBar? = null

    private val colorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            mTvStatusAlpha!!.text = mAlpha.toString()
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    override fun initData(bundle: Bundle?) {
        mRandom = Random()
        mColor = ContextCompat.getColor(UtilsApp.instance, R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_color
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        findViewById<View>(R.id.btn_random_color).setOnClickListener(this)
        findViewById<View>(R.id.btn_set_transparent).setOnClickListener(this)
        mTvStatusAlpha = findViewById(R.id.tv_status_alpha)
        sbChangeAlpha = findViewById(R.id.sb_change_alpha)
        sbChangeAlpha!!.setOnSeekBarChangeListener(colorListener)
        mTvStatusAlpha!!.text = mAlpha.toString()

        updateStatusBar()
    }


    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_random_color -> {
                mColor = -0x1000000 or mRandom!!.nextInt(0xffffff)
                updateStatusBar()
            }
            R.id.btn_set_transparent -> sbChangeAlpha!!.progress = 0
        }
    }

    private fun updateStatusBar() {
        BarUtils.setStatusBarColor(this, mColor, mAlpha)
        BarUtils.addMarginTopEqualStatusBarHeight(mTvStatusAlpha!!)// 其实这个只需要调用一次即可
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarStatusColorActivity::class.java)
            context.startActivity(starter)
        }
    }
}
