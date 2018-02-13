package com.blankj.androidutilcode.feature.core.span

import android.graphics.Color
import android.support.annotation.ColorInt
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

class ForegroundAlphaColorSpan(@param:ColorInt private var mColor: Int) : CharacterStyle(), UpdateAppearance {

    fun setAlpha(alpha: Int) {
        mColor = Color.argb(alpha, Color.red(mColor), Color.green(mColor), Color.blue(mColor))
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = mColor
    }
}
