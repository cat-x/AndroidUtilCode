package com.blankj.androidutilcode.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/03/28
 * desc  : Fragment－v4 基类
</pre> *
 */
abstract class BaseFragment : Fragment(), IBaseView {
    /**
     * 当前 Activity 渲染的视图 View
     */
    protected var contentView: View? = null
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
        if (savedInstanceState != null) {
            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
            val ft = fragmentManager.beginTransaction()
            if (isSupportHidden) {
                ft.hide(this)
            } else {
                ft.show(this)
            }
            ft.commit()
        }
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        contentView = inflater!!.inflate(bindLayout(), null)
        Log.d(TAG, "onCreateView: ")
        return contentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        initData(bundle)
        Log.d(TAG, "onViewCreated: ")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mActivity = activity as BaseActivity
        initView(savedInstanceState, contentView)
        doBusiness()
        Log.d(TAG, "onActivityCreated: ")
    }

    override fun onClick(view: View) {
        if (!isFastClick) onWidgetClick(view)
    }

    override fun onDestroyView() {
        if (contentView != null) {
            (contentView!!.parent as ViewGroup).removeView(contentView)
        }
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
    }

    companion object {

        private val TAG = "BaseFragment"

        private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
    }
}
