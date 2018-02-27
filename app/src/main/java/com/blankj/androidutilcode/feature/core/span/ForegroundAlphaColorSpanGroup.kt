package com.blankj.androidutilcode.feature.core.span

import java.util.*

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2017/06/11
 * desc  :
</pre> *
 */
class ForegroundAlphaColorSpanGroup(private val mAlpha: Float) {

    private val mSpans: ArrayList<ForegroundAlphaColorSpan> = ArrayList()

    var alpha: Float
        get() = mAlpha
        set(alpha) {
            val size = mSpans.size
            var total = 1.0f * size.toFloat() * alpha
            (0 until size)
                    .asSequence()
                    .map { mSpans[it] }
                    .forEach {
                        if (total >= 1.0f) {
                            it.setAlpha(255)
                            total -= 1.0f
                        } else {
                            it.setAlpha((total * 255).toInt())
                            total = 0.0f
                        }
                    }
        }

    fun addSpan(span: ForegroundAlphaColorSpan) {
        span.setAlpha((mAlpha * 255).toInt())
        mSpans.add(span)
    }
}
