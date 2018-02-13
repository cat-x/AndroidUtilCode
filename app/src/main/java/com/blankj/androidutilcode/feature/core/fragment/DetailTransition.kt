package com.blankj.androidutilcode.feature.core.fragment

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.AttributeSet

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 17/02/21
 * desc  :
</pre> *
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class DetailTransition : TransitionSet {
    constructor() {
        init()
    }

    // 允许资源文件使用
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform()).addTransition(ChangeImageTransform())
    }
}
