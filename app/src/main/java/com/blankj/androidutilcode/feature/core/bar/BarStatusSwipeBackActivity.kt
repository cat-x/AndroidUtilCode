package com.blankj.androidutilcode.feature.core.bar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.UtilsApp
import com.blankj.androidutilcode.base.BaseActivity
import com.blankj.utilcode.util.BarUtils
import com.r0adkll.slidr.Slidr
import java.util.*


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/05/27
 * desc  : Bar 工具类 Demo
</pre> *
 */
class BarStatusSwipeBackActivity : BaseActivity() {

    private var mRandom: Random? = null
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private var llContainer: LinearLayout? = null
    private var cbAlpha: CheckBox? = null
    private var tvStatusAlpha: TextView? = null
    private var sbChangeAlpha: SeekBar? = null
    private var btnRandomColor: Button? = null

    private val mColorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            tvStatusAlpha!!.text = mAlpha.toString()
            updateStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    internal var mCheckedChangeListener: CompoundButton.OnCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
        if (isChecked) {
            btnRandomColor!!.visibility = View.GONE
            llContainer!!.setBackgroundResource(R.drawable.bg_bar)
        } else {
            btnRandomColor!!.visibility = View.VISIBLE
            llContainer!!.setBackgroundColor(Color.WHITE)
        }
        updateStatusBar()
    }

    override fun initData(bundle: Bundle?) {
        mRandom = Random()
        mColor = ContextCompat.getColor(UtilsApp.instance, R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.activity_bar_status_swipe_back
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        Slidr.attach(this)

        llContainer = findViewById(R.id.ll_container)
        cbAlpha = findViewById(R.id.cb_alpha)
        btnRandomColor = findViewById(R.id.btn_random_color)
        tvStatusAlpha = findViewById(R.id.tv_status_alpha)
        sbChangeAlpha = findViewById(R.id.sb_change_alpha)

        cbAlpha!!.setOnCheckedChangeListener(mCheckedChangeListener)
        btnRandomColor!!.setOnClickListener(this)
        findViewById<View>(R.id.btn_set_transparent).setOnClickListener(this)
        sbChangeAlpha!!.setOnSeekBarChangeListener(mColorListener)

        tvStatusAlpha!!.text = mAlpha.toString()

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
        if (cbAlpha!!.isChecked) {
            BarUtils.setStatusBarAlpha(this, mAlpha)
            BarUtils.addMarginTopEqualStatusBarHeight(cbAlpha!!)
        } else {
            BarUtils.setStatusBarColor(this, mColor, mAlpha)
            BarUtils.addMarginTopEqualStatusBarHeight(cbAlpha!!)
        }
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, BarStatusSwipeBackActivity::class.java)
            context.startActivity(starter)
        }
    }
}
