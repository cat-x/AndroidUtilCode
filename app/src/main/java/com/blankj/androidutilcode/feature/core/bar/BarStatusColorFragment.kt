package com.blankj.androidutilcode.feature.core.bar

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.UtilsApp
import com.blankj.androidutilcode.base.BaseFragment
import com.blankj.utilcode.util.BarUtils
import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : Bar 工具类 Demo
</pre> *
 */
class BarStatusColorFragment : BaseFragment() {

    private var mRandom: Random? = null
    private var mColor: Int = 0
    private var mAlpha: Int = 0

    private var mTvStatusAlpha: TextView? = null
    private var sbChangeAlpha: SeekBar? = null
    private var fakeStatusBar: View? = null

    private val colorListener = object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            mAlpha = progress
            mTvStatusAlpha!!.text = mAlpha.toString()
            updateFakeStatusBar()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {

        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {

        }
    }

    override fun initData(bundle: Bundle?) {
        mRandom = Random()
        mColor = ContextCompat.getColor(UtilsApp.instance!!, R.color.colorPrimary)
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_color
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        if (view != null) {
            fakeStatusBar = view.findViewById(R.id.fake_status_bar)
            view.findViewById<View>(R.id.btn_random_color).setOnClickListener(this)
            view.findViewById<View>(R.id.btn_set_transparent).setOnClickListener(this)
            mTvStatusAlpha = view.findViewById<View>(R.id.tv_status_alpha) as TextView
            sbChangeAlpha = view.findViewById<View>(R.id.sb_change_alpha) as SeekBar
            sbChangeAlpha!!.setOnSeekBarChangeListener(colorListener)
            mTvStatusAlpha!!.text = mAlpha.toString()
        }
        updateFakeStatusBar()
    }


    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_random_color -> {
                mColor = -0x1000000 or mRandom!!.nextInt(0xffffff)
                updateFakeStatusBar()
            }
            R.id.btn_set_transparent -> sbChangeAlpha!!.progress = 0
        }
    }

    fun updateFakeStatusBar() {
        BarUtils.setStatusBarColor(fakeStatusBar!!, mColor, mAlpha)
    }

    companion object {

        fun newInstance(): BarStatusColorFragment {
            return BarStatusColorFragment()
        }
    }
}
