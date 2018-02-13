package com.blankj.androidutilcode.feature.core.span

import android.graphics.BlurMaskFilter
import android.graphics.MaskFilter
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

class BlurMaskFilterSpan(private var mRadius: Float) : CharacterStyle(), UpdateAppearance {
    private var mFilter: MaskFilter? = null

    var radius: Float
        get() = mRadius
        set(radius) {
            mRadius = radius
            mFilter = BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL)
        }

    override fun updateDrawState(ds: TextPaint) {
        ds.maskFilter = mFilter
    }
}
