package com.blankj.androidutilcode.base.rv.listener

import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/21
 * desc  :
</pre> *
 */
interface OnItemLongClickListener {
    fun onItemLongClick(view: View, position: Int): Boolean
}
