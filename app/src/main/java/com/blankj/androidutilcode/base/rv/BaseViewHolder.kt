package com.blankj.androidutilcode.base.rv

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/08/22
 * desc  :
</pre> *
 */
class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewArray = SparseArray<View>()

    fun <T : View> getView(@IdRes viewId: Int): T {
        var view: View? = viewArray.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            viewArray.put(viewId, view)
        }
        return view as T
    }
}
