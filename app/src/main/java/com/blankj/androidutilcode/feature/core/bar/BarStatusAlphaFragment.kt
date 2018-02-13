package com.blankj.androidutilcode.feature.core.bar

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.BaseFragment
import com.blankj.utilcode.util.BarUtils

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/07/01
 * desc  : Bar 工具类 Demo
</pre> *
 */
class BarStatusAlphaFragment : BaseFragment() {

    private var mAlpha: Int = 0

    private var mTvStatusAlpha: TextView? = null
    private var sbChangeAlpha: SeekBar? = null
    private var fakeStatusBar: View? = null

    private val translucentListener = object : SeekBar.OnSeekBarChangeListener {
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
        mAlpha = 112
    }

    override fun bindLayout(): Int {
        return R.layout.fragment_bar_status_alpha
    }

    override fun initView(savedInstanceState: Bundle?, view: View?) {
        if (view != null) {
            fakeStatusBar = view.findViewById(R.id.fake_status_bar)
            mTvStatusAlpha = view.findViewById(R.id.tv_status_alpha)
            sbChangeAlpha = view.findViewById(R.id.sb_change_alpha)
            view.findViewById<View>(R.id.btn_set_transparent).setOnClickListener(this)
            sbChangeAlpha!!.setOnSeekBarChangeListener(translucentListener)
            mTvStatusAlpha!!.text = mAlpha.toString()
        }
        updateFakeStatusBar()
    }

    override fun doBusiness() {

    }

    override fun onWidgetClick(view: View) {
        when (view.id) {
            R.id.btn_set_transparent -> sbChangeAlpha!!.progress = 0
        }
    }

    fun updateFakeStatusBar() {
        BarUtils.setStatusBarAlpha(fakeStatusBar!!, mAlpha)
    }

    companion object {


        fun newInstance(): BarStatusAlphaFragment {
            return BarStatusAlphaFragment()
        }
    }
}
