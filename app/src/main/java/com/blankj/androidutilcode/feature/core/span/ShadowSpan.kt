package com.blankj.androidutilcode.feature.core.span

import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

class ShadowSpan(private val radius: Float, var dx: Float, var dy: Float, private val shadowColor: Int) : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(tp: TextPaint) {
        tp.setShadowLayer(radius, dx, dy, shadowColor)
    }
}
