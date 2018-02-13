package com.blankj.androidutilcode.feature.core.image

import android.graphics.Bitmap
import android.support.annotation.StringRes

import com.blankj.utilcode.util.Utils


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/09/18
 * desc  :
</pre> *
 */
class ImageBean {

    internal var resId: Int = 0
    var name: String
    var image: Bitmap?

    constructor(@StringRes resId: Int, image: Bitmap?) {
        name = Utils.app.getString(resId)
        this.image = image
    }

    constructor(name: String, image: Bitmap?) {
        this.name = name
        this.image = image
    }
}
