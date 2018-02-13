package com.blankj.androidutilcode.feature.core.image

import android.support.annotation.LayoutRes
import android.widget.ImageView
import android.widget.TextView

import com.blankj.androidutilcode.R
import com.blankj.androidutilcode.base.rv.BaseViewHolder
import com.blankj.androidutilcode.base.rv.adapter.SingleAdapter

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/09/18
 * desc  :
</pre> *
 */
class ImageAdapter(list: List<ImageBean>, @LayoutRes layoutId: Int) : SingleAdapter<ImageBean>(list, layoutId) {

    override fun bind(holder: BaseViewHolder, data: ImageBean) {
        val textView = holder.getView<TextView>(R.id.tv_image_name)
        textView.text = data.name
        val image = holder.getView<ImageView>(R.id.iv_image)
        image.setImageBitmap(data.image)
    }
}
