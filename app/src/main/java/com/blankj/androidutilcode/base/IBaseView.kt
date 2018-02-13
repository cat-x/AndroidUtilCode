package com.blankj.androidutilcode.base

import android.os.Bundle
import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/06/27
 * desc  :
</pre> *
 */
internal interface IBaseView : View.OnClickListener {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的 bundle
     */
    fun initData(bundle: Bundle?)

    /**
     * 绑定布局
     *
     * @return 布局 Id
     */
    fun bindLayout(): Int

    /**
     * 初始化 view
     */
    fun initView(savedInstanceState: Bundle?, view: View?)

    /**
     * 业务操作
     */
    fun doBusiness()

    /**
     * 视图点击事件
     *
     * @param view 视图
     */
    fun onWidgetClick(view: View)
}
