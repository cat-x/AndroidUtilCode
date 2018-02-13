package com.blankj.androidutilcode.base.rv.adapter

import android.support.annotation.LayoutRes

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/22
 * desc  :
</pre> *
 */
abstract class SingleAdapter<M>(list: List<M>, @param:LayoutRes private val mLayoutId: Int) : BaseAdapter<M>() {

    init {
        setData(list)
    }

    override fun bindLayout(viewType: Int): Int {
        return mLayoutId
    }

}
