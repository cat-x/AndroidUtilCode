package com.blankj.androidutilcode.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/24
 * desc  : Activity 基类
</pre> *
 */
abstract class BaseActivity : AppCompatActivity(), IBaseView {

    /**
     * 当前 Activity 渲染的视图 View
     */
    protected lateinit var mContentView: View
    /**
     * 上次点击时间
     */
    private var lastClick: Long = 0

    protected lateinit var mActivity: BaseActivity

    /**
     * 判断是否快速点击
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    private val isFastClick: Boolean
        get() {
            val now = System.currentTimeMillis()
            if (now - lastClick >= 200) {
                lastClick = now
                return false
            }
            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        val bundle = intent.extras
        initData(bundle)
        setBaseView(bindLayout())
        initView(savedInstanceState, mContentView)
        doBusiness()
    }

    protected open fun setBaseView(@LayoutRes layoutId: Int) {
        mContentView = LayoutInflater.from(this).inflate(layoutId, null)
        setContentView(mContentView)
    }

    override fun onClick(view: View) {
        if (!isFastClick) onWidgetClick(view)
    }
}
