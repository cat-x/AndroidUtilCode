package com.blankj.utilcode.util

import android.annotation.SuppressLint
import android.graphics.*
import android.graphics.BlurMaskFilter.Blur
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.*
import android.support.annotation.IntRange
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.text.Layout.Alignment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.*
import android.util.Log
import java.lang.ref.WeakReference

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 16/12/13
 * desc  : SpannableString 相关工具类
</pre> *
 */
class SpanUtils {

    private var mText: CharSequence? = null
    private var flag: Int = 0
    private var foregroundColor: Int = 0
    private var backgroundColor: Int = 0
    private var lineHeight: Int = 0
    private var alignLine: Int = 0
    private var quoteColor: Int = 0
    private var stripeWidth: Int = 0
    private var quoteGapWidth: Int = 0
    private var first: Int = 0
    private var rest: Int = 0
    private var bulletColor: Int = 0
    private var bulletRadius: Int = 0
    private var bulletGapWidth: Int = 0
    private var fontSize: Int = 0
    private var fontSizeIsDp: Boolean = false
    private var proportion: Float = 0.toFloat()
    private var xProportion: Float = 0.toFloat()
    private var isStrikethrough: Boolean = false
    private var isUnderline: Boolean = false
    private var isSuperscript: Boolean = false
    private var isSubscript: Boolean = false
    private var isBold: Boolean = false
    private var isItalic: Boolean = false
    private var isBoldItalic: Boolean = false
    private var fontFamily: String? = null
    private var typeface: Typeface? = null
    private var alignment: Alignment? = null
    private var clickSpan: ClickableSpan? = null
    private var url: String? = null
    private var blurRadius: Float = 0.toFloat()
    private var style: Blur? = null
    private var shader: Shader? = null
    private var shadowRadius: Float = 0.toFloat()
    private var shadowDx: Float = 0.toFloat()
    private var shadowDy: Float = 0.toFloat()
    private var shadowColor: Int = 0
    private var spans: Array<out Any>? = null

    private var imageBitmap: Bitmap? = null
    private var imageDrawable: Drawable? = null
    private var imageUri: Uri? = null
    private var imageResourceId: Int = 0
    private var alignImage: Int = 0

    private var spaceSize: Int = 0
    private var spaceColor: Int = 0

    private val mBuilder: SpannableStringBuilder = SpannableStringBuilder()

    private var mType: Int = 0
    private val mTypeCharSequence = 0
    private val mTypeImage = 1
    private val mTypeSpace = 2

    init {
        mText = ""
        setDefault()
    }

    private fun setDefault() {
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        foregroundColor = COLOR_DEFAULT
        backgroundColor = COLOR_DEFAULT
        lineHeight = -1
        quoteColor = COLOR_DEFAULT
        first = -1
        bulletColor = COLOR_DEFAULT
        fontSize = -1
        proportion = -1f
        xProportion = -1f
        isStrikethrough = false
        isUnderline = false
        isSuperscript = false
        isSubscript = false
        isBold = false
        isItalic = false
        isBoldItalic = false
        fontFamily = null
        typeface = null
        alignment = null
        clickSpan = null
        url = null
        blurRadius = -1f
        shader = null
        shadowRadius = -1f
        spans = null

        imageBitmap = null
        imageDrawable = null
        imageUri = null
        imageResourceId = -1

        spaceSize = -1
    }

    /**
     * 设置标识
     *
     * @param flag
     *  * [Spanned.SPAN_INCLUSIVE_EXCLUSIVE]
     *  * [Spanned.SPAN_INCLUSIVE_INCLUSIVE]
     *  * [Spanned.SPAN_EXCLUSIVE_EXCLUSIVE]
     *  * [Spanned.SPAN_EXCLUSIVE_INCLUSIVE]
     *
     * @return [SpanUtils]
     */
    fun setFlag(flag: Int): SpanUtils {
        this.flag = flag
        return this
    }

    /**
     * 设置前景色
     *
     * @param color 前景色
     * @return [SpanUtils]
     */
    fun setForegroundColor(@ColorInt color: Int): SpanUtils {
        this.foregroundColor = color
        return this
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return [SpanUtils]
     */
    fun setBackgroundColor(@ColorInt color: Int): SpanUtils {
        this.backgroundColor = color
        return this
    }

    /**
     * 设置行高
     *
     * 当行高大于字体高度时，字体在行中的位置由`align`决定
     *
     * @param lineHeight 行高
     * @param align      对齐
     *
     *  * [Align.ALIGN_TOP]顶部对齐
     *  * [Align.ALIGN_CENTER]居中对齐
     *  * [Align.ALIGN_BOTTOM]底部对齐
     *
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun setLineHeight(@IntRange(from = 0) lineHeight: Int,
                      @Align align: Int = ALIGN_CENTER): SpanUtils {
        this.lineHeight = lineHeight
        this.alignLine = align
        return this
    }

    /**
     * 设置引用线的颜色
     *
     * @param color       引用线的颜色
     * @param stripeWidth 引用线线宽
     * @param gapWidth    引用线和文字间距
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun setQuoteColor(@ColorInt color: Int,
                      @IntRange(from = 1) stripeWidth: Int = 2,
                      @IntRange(from = 0) gapWidth: Int = 2): SpanUtils {
        this.quoteColor = color
        this.stripeWidth = stripeWidth
        this.quoteGapWidth = gapWidth
        return this
    }

    /**
     * 设置缩进
     *
     * @param first 首行缩进
     * @param rest  剩余行缩进
     * @return [SpanUtils]
     */
    fun setLeadingMargin(@IntRange(from = 0) first: Int,
                         @IntRange(from = 0) rest: Int): SpanUtils {
        this.first = first
        this.rest = rest
        return this
    }

    /**
     * 设置列表标记
     *
     * @param gapWidth 列表标记和文字间距离
     * @return [SpanUtils]
     */
    fun setBullet(@IntRange(from = 0) gapWidth: Int): SpanUtils {
        return setBullet(0, 3, gapWidth)
    }

    /**
     * 设置列表标记
     *
     * @param color    列表标记的颜色
     * @param radius   列表标记颜色
     * @param gapWidth 列表标记和文字间距离
     * @return [SpanUtils]
     */
    fun setBullet(@ColorInt color: Int,
                  @IntRange(from = 0) radius: Int,
                  @IntRange(from = 0) gapWidth: Int): SpanUtils {
        this.bulletColor = color
        this.bulletRadius = radius
        this.bulletGapWidth = gapWidth
        return this
    }

    /**
     * 设置字体尺寸
     *
     * @param size 尺寸
     * @param isDp 是否使用 dip
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun setFontSize(@IntRange(from = 0) size: Int, isDp: Boolean = false): SpanUtils {
        this.fontSize = size
        this.fontSizeIsDp = isDp
        return this
    }

    /**
     * 设置字体比例
     *
     * @param proportion 比例
     * @return [SpanUtils]
     */
    fun setFontProportion(proportion: Float): SpanUtils {
        this.proportion = proportion
        return this
    }

    /**
     * 设置字体横向比例
     *
     * @param proportion 比例
     * @return [SpanUtils]
     */
    fun setFontXProportion(proportion: Float): SpanUtils {
        this.xProportion = proportion
        return this
    }

    /**
     * 设置删除线
     *
     * @return [SpanUtils]
     */
    fun setStrikethrough(): SpanUtils {
        this.isStrikethrough = true
        return this
    }

    /**
     * 设置下划线
     *
     * @return [SpanUtils]
     */
    fun setUnderline(): SpanUtils {
        this.isUnderline = true
        return this
    }

    /**
     * 设置上标
     *
     * @return [SpanUtils]
     */
    fun setSuperscript(): SpanUtils {
        this.isSuperscript = true
        return this
    }

    /**
     * 设置下标
     *
     * @return [SpanUtils]
     */
    fun setSubscript(): SpanUtils {
        this.isSubscript = true
        return this
    }

    /**
     * 设置粗体
     *
     * @return [SpanUtils]
     */
    fun setBold(): SpanUtils {
        isBold = true
        return this
    }

    /**
     * 设置斜体
     *
     * @return [SpanUtils]
     */
    fun setItalic(): SpanUtils {
        isItalic = true
        return this
    }

    /**
     * 设置粗斜体
     *
     * @return [SpanUtils]
     */
    fun setBoldItalic(): SpanUtils {
        isBoldItalic = true
        return this
    }

    /**
     * 设置字体系列
     *
     * @param fontFamily 字体系列
     *
     *  * monospace
     *  * serif
     *  * sans-serif
     *
     * @return [SpanUtils]
     */
    fun setFontFamily(fontFamily: String): SpanUtils {
        this.fontFamily = fontFamily
        return this
    }

    /**
     * 设置字体
     *
     * @param typeface 字体
     * @return [SpanUtils]
     */
    fun setTypeface(typeface: Typeface): SpanUtils {
        this.typeface = typeface
        return this
    }

    /**
     * 设置对齐
     *
     * @param alignment 对其方式
     *
     *  * [Alignment.ALIGN_NORMAL]正常
     *  * [Alignment.ALIGN_OPPOSITE]相反
     *  * [Alignment.ALIGN_CENTER]居中
     *
     * @return [SpanUtils]
     */
    fun setAlign(alignment: Alignment): SpanUtils {
        this.alignment = alignment
        return this
    }

    /**
     * 设置点击事件
     *
     * 需添加 view.setMovementMethod(LinkMovementMethod.getInstance())
     *
     * @param clickSpan 点击事件
     * @return [SpanUtils]
     */
    fun setClickSpan(clickSpan: ClickableSpan): SpanUtils {
        this.clickSpan = clickSpan
        return this
    }

    /**
     * 设置超链接
     *
     * 需添加 view.setMovementMethod(LinkMovementMethod.getInstance())
     *
     * @param url 超链接
     * @return [SpanUtils]
     */
    fun setUrl(url: String): SpanUtils {
        this.url = url
        return this
    }

    /**
     * 设置模糊
     *
     * 尚存 bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊
     *
     * 以上 bug 关闭硬件加速即可
     *
     * @param radius 模糊半径（需大于 0）
     * @param style  模糊样式
     *  * [Blur.NORMAL]
     *  * [Blur.SOLID]
     *  * [Blur.OUTER]
     *  * [Blur.INNER]
     *
     * @return [SpanUtils]
     */
    fun setBlur(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
                style: Blur): SpanUtils {
        this.blurRadius = radius
        this.style = style
        return this
    }

    /**
     * 设置着色器
     *
     * @param shader 着色器
     * @return [SpanUtils]
     */
    fun setShader(shader: Shader): SpanUtils {
        this.shader = shader
        return this
    }

    /**
     * 设置阴影
     *
     * @param radius      阴影半径
     * @param dx          x 轴偏移量
     * @param dy          y 轴偏移量
     * @param shadowColor 阴影颜色
     * @return [SpanUtils]
     */
    fun setShadow(@FloatRange(from = 0.0, fromInclusive = false) radius: Float,
                  dx: Float,
                  dy: Float,
                  shadowColor: Int): SpanUtils {
        this.shadowRadius = radius
        this.shadowDx = dx
        this.shadowDy = dy
        this.shadowColor = shadowColor
        return this
    }


    /**
     * 设置样式
     *
     * @param spans 样式
     * @return [SpanUtils]
     */
    fun setSpans(vararg spans: Any): SpanUtils {
        if (spans.isNotEmpty()) {
            this.spans = spans
        }
        return this
    }

    /**
     * 追加样式字符串
     *
     * @param text 样式字符串文本
     * @return [SpanUtils]
     */
    fun append(text: CharSequence): SpanUtils {
        apply(mTypeCharSequence)
        mText = text
        return this
    }

    /**
     * 追加一行
     *
     * @return [SpanUtils]
     */
    fun appendLine(): SpanUtils {
        apply(mTypeCharSequence)
        mText = LINE_SEPARATOR
        return this
    }

    /**
     * 追加一行样式字符串
     *
     * @return [SpanUtils]
     */
    fun appendLine(text: CharSequence): SpanUtils {
        apply(mTypeCharSequence)
        mText = text.toString() + LINE_SEPARATOR
        return this
    }

    /**
     * 追加图片
     *
     * @param bitmap 图片位图
     * @param align  对齐
     *
     *  * [Align.ALIGN_TOP]顶部对齐
     *  * [Align.ALIGN_CENTER]居中对齐
     *  * [Align.ALIGN_BASELINE]基线对齐
     *  * [Align.ALIGN_BOTTOM]底部对齐
     *
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun appendImage(bitmap: Bitmap, @Align align: Int = ALIGN_BOTTOM): SpanUtils {
        apply(mTypeImage)
        this.imageBitmap = bitmap
        this.alignImage = align
        return this
    }

    /**
     * 追加图片
     *
     * @param drawable 图片资源
     * @param align    对齐
     *
     *  * [Align.ALIGN_TOP]顶部对齐
     *  * [Align.ALIGN_CENTER]居中对齐
     *  * [Align.ALIGN_BASELINE]基线对齐
     *  * [Align.ALIGN_BOTTOM]底部对齐
     *
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun appendImage(drawable: Drawable, @Align align: Int = ALIGN_BOTTOM): SpanUtils {
        apply(mTypeImage)
        this.imageDrawable = drawable
        this.alignImage = align
        return this
    }

    /**
     * 追加图片
     *
     * @param uri   图片 uri
     * @param align 对齐
     *
     *  * [Align.ALIGN_TOP]顶部对齐
     *  * [Align.ALIGN_CENTER]居中对齐
     *  * [Align.ALIGN_BASELINE]基线对齐
     *  * [Align.ALIGN_BOTTOM]底部对齐
     *
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun appendImage(uri: Uri, @Align align: Int = ALIGN_BOTTOM): SpanUtils {
        apply(mTypeImage)
        this.imageUri = uri
        this.alignImage = align
        return this
    }

    /**
     * 追加图片
     *
     * @param resourceId 图片资源 id
     * @param align      对齐
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun appendImage(@DrawableRes resourceId: Int, @Align align: Int = ALIGN_BOTTOM): SpanUtils {
        append(Character.toString(0.toChar()))// it's important for span start with image
        apply(mTypeImage)
        this.imageResourceId = resourceId
        this.alignImage = align
        return this
    }

    /**
     * 追加空白
     *
     * @param size  间距
     * @param color 颜色
     * @return [SpanUtils]
     */
    @JvmOverloads
    fun appendSpace(@IntRange(from = 0) size: Int, @ColorInt color: Int = Color.TRANSPARENT): SpanUtils {
        apply(mTypeSpace)
        spaceSize = size
        spaceColor = color
        return this
    }

    private fun apply(type: Int) {
        applyLast()
        mType = type
    }

    /**
     * 创建样式字符串
     *
     * @return 样式字符串
     */
    fun create(): SpannableStringBuilder {
        applyLast()
        return mBuilder
    }

    /**
     * 设置上一次的样式
     */
    private fun applyLast() {
        when (mType) {
            mTypeCharSequence -> updateCharCharSequence()
            mTypeImage -> updateImage()
            mTypeSpace -> updateSpace()
        }
        setDefault()
    }

    private fun updateCharCharSequence() {
        if (mText!!.isEmpty()) return
        val start = mBuilder.length
        mBuilder.append(mText)
        val end = mBuilder.length
        if (foregroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(ForegroundColorSpan(foregroundColor), start, end, flag)
        }
        if (backgroundColor != COLOR_DEFAULT) {
            mBuilder.setSpan(BackgroundColorSpan(backgroundColor), start, end, flag)
        }
        if (first != -1) {
            mBuilder.setSpan(LeadingMarginSpan.Standard(first, rest), start, end, flag)
        }
        if (quoteColor != COLOR_DEFAULT) {
            mBuilder.setSpan(
                    CustomQuoteSpan(quoteColor, stripeWidth, quoteGapWidth),
                    start,
                    end,
                    flag
            )
        }
        if (bulletColor != COLOR_DEFAULT) {
            mBuilder.setSpan(
                    CustomBulletSpan(bulletColor, bulletRadius, bulletGapWidth),
                    start,
                    end,
                    flag
            )
        }
        //        if (imGapWidth != -1) {
        //            if (imBitmap != null) {
        //                mBuilder.setSpan(
        //                        new CustomIconMarginSpan(imBitmap, imGapWidth, imAlign),
        //                        start,
        //                        end,
        //                        flag
        //                );
        //            } else if (imDrawable != null) {
        //                mBuilder.setSpan(
        //                        new CustomIconMarginSpan(imDrawable, imGapWidth, imAlign),
        //                        start,
        //                        end,
        //                        flag
        //                );
        //            } else if (imUri != null) {
        //                mBuilder.setSpan(
        //                        new CustomIconMarginSpan(imUri, imGapWidth, imAlign),
        //                        start,
        //                        end,
        //                        flag
        //                );
        //            } else if (imResourceId != -1) {
        //                mBuilder.setSpan(
        //                        new CustomIconMarginSpan(imResourceId, imGapWidth, imAlign),
        //                        start,
        //                        end,
        //                        flag
        //                );
        //            }
        //        }
        if (fontSize != -1) {
            mBuilder.setSpan(AbsoluteSizeSpan(fontSize, fontSizeIsDp), start, end, flag)
        }
        if (proportion != -1f) {
            mBuilder.setSpan(RelativeSizeSpan(proportion), start, end, flag)
        }
        if (xProportion != -1f) {
            mBuilder.setSpan(ScaleXSpan(xProportion), start, end, flag)
        }
        if (lineHeight != -1) {
            mBuilder.setSpan(CustomLineHeightSpan(lineHeight, alignLine), start, end, flag)
        }
        if (isStrikethrough) {
            mBuilder.setSpan(StrikethroughSpan(), start, end, flag)
        }
        if (isUnderline) {
            mBuilder.setSpan(UnderlineSpan(), start, end, flag)
        }
        if (isSuperscript) {
            mBuilder.setSpan(SuperscriptSpan(), start, end, flag)
        }
        if (isSubscript) {
            mBuilder.setSpan(SubscriptSpan(), start, end, flag)
        }
        if (isBold) {
            mBuilder.setSpan(StyleSpan(Typeface.BOLD), start, end, flag)
        }
        if (isItalic) {
            mBuilder.setSpan(StyleSpan(Typeface.ITALIC), start, end, flag)
        }
        if (isBoldItalic) {
            mBuilder.setSpan(StyleSpan(Typeface.BOLD_ITALIC), start, end, flag)
        }
        if (fontFamily != null) {
            mBuilder.setSpan(TypefaceSpan(fontFamily), start, end, flag)
        }
        if (typeface != null) {
            mBuilder.setSpan(CustomTypefaceSpan(typeface!!), start, end, flag)
        }
        if (alignment != null) {
            mBuilder.setSpan(AlignmentSpan.Standard(alignment), start, end, flag)
        }
        if (clickSpan != null) {
            mBuilder.setSpan(clickSpan, start, end, flag)
        }
        if (url != null) {
            mBuilder.setSpan(URLSpan(url), start, end, flag)
        }
        if (blurRadius != -1f) {
            mBuilder.setSpan(
                    MaskFilterSpan(BlurMaskFilter(blurRadius, style)),
                    start,
                    end,
                    flag
            )
        }
        if (shader != null) {
            mBuilder.setSpan(ShaderSpan(shader!!), start, end, flag)
        }
        if (shadowRadius != -1f) {
            mBuilder.setSpan(
                    ShadowSpan(shadowRadius, shadowDx, shadowDy, shadowColor),
                    start,
                    end,
                    flag
            )
        }
        if (spans != null) {
            for (span in spans!!) {
                mBuilder.setSpan(span, start, end, flag)
            }
        }
    }

    private fun updateImage() {
        val start = mBuilder.length
        mBuilder.append("<img>")
        val end = start + 5
        when {
            imageBitmap != null -> mBuilder.setSpan(CustomImageSpan(imageBitmap!!, alignImage), start, end, flag)
            imageDrawable != null -> mBuilder.setSpan(CustomImageSpan(imageDrawable!!, alignImage), start, end, flag)
            imageUri != null -> mBuilder.setSpan(CustomImageSpan(imageUri!!, alignImage), start, end, flag)
            imageResourceId != -1 -> mBuilder.setSpan(CustomImageSpan(imageResourceId, alignImage), start, end, flag)
        }
    }

    private fun updateSpace() {
        val start = mBuilder.length
        mBuilder.append("< >")
        val end = start + 3
        mBuilder.setSpan(SpaceSpan(spaceSize, spaceColor), start, end, flag)
    }

    /**
     * 行高
     */
    internal inner class CustomLineHeightSpan(private val height: Int, val mVerticalAlignment: Int) : CharacterStyle(), LineHeightSpan {

        override fun chooseHeight(text: CharSequence, start: Int, end: Int,
                                  spanstartv: Int, v: Int, fm: Paint.FontMetricsInt) {
            var need = height - (v + fm.descent - fm.ascent - spanstartv)
            //            if (need > 0) {
            when (mVerticalAlignment) {
                ALIGN_TOP -> fm.descent += need
                ALIGN_CENTER -> {
                    fm.descent += need / 2
                    fm.ascent -= need / 2
                }
                else -> fm.ascent -= need
            }
            //            }
            need = height - (v + fm.bottom - fm.top - spanstartv)
            //            if (need > 0) {
            when (mVerticalAlignment) {
                ALIGN_TOP -> fm.top += need
                ALIGN_CENTER -> {
                    fm.bottom += need / 2
                    fm.top -= need / 2
                }
                else -> fm.top -= need
            }
            //            }
        }

        override fun updateDrawState(tp: TextPaint) {

        }

    }

    /**
     * 空格
     */
    internal inner class SpaceSpan constructor(private val width: Int, private val color: Int = Color.TRANSPARENT) : ReplacementSpan() {

        override fun getSize(paint: Paint, text: CharSequence,
                             @IntRange(from = 0) start: Int,
                             @IntRange(from = 0) end: Int,
                             fm: Paint.FontMetricsInt?): Int {
            return width
        }

        override fun draw(canvas: Canvas, text: CharSequence,
                          @IntRange(from = 0) start: Int,
                          @IntRange(from = 0) end: Int,
                          x: Float, top: Int, y: Int, bottom: Int,
                          paint: Paint) {
            val style = paint.style
            val color = paint.color

            paint.style = Paint.Style.FILL
            paint.color = this.color

            canvas.drawRect(x, top.toFloat(), x + width, bottom.toFloat(), paint)

            paint.style = style
            paint.color = color
        }
    }

    /**
     * 引用
     */
    internal inner class CustomQuoteSpan constructor(private val color: Int, private val stripeWidth: Int, private val gapWidth: Int) : LeadingMarginSpan {

        override fun getLeadingMargin(first: Boolean): Int {
            return stripeWidth + gapWidth
        }

        override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                       top: Int, baseline: Int, bottom: Int,
                                       text: CharSequence, start: Int, end: Int,
                                       first: Boolean, layout: Layout) {
            val style = p.style
            val color = p.color

            p.style = Paint.Style.FILL
            p.color = this.color

            c.drawRect(x.toFloat(), top.toFloat(), (x + dir * stripeWidth).toFloat(), bottom.toFloat(), p)

            p.style = style
            p.color = color
        }
    }

    /**
     * 列表项
     */
    internal inner class CustomBulletSpan constructor(private val color: Int, private val radius: Int, private val gapWidth: Int) : LeadingMarginSpan {

        private var sBulletPath: Path? = null

        override fun getLeadingMargin(first: Boolean): Int {
            return 2 * radius + gapWidth
        }

        override fun drawLeadingMargin(c: Canvas, p: Paint, x: Int, dir: Int,
                                       top: Int, baseline: Int, bottom: Int,
                                       text: CharSequence, start: Int, end: Int,
                                       first: Boolean, l: Layout) {
            if ((text as Spanned).getSpanStart(this) == start) {
                val style = p.style
                var oldColor = 0
                oldColor = p.color
                p.color = color
                p.style = Paint.Style.FILL
                if (c.isHardwareAccelerated) {
                    if (sBulletPath == null) {
                        sBulletPath = Path()
                        // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                        sBulletPath!!.addCircle(0.0f, 0.0f, radius.toFloat(), Path.Direction.CW)
                    }
                    c.save()
                    c.translate((x + dir * radius).toFloat(), (top + bottom) / 2.0f)
                    c.drawPath(sBulletPath!!, p)
                    c.restore()
                } else {
                    c.drawCircle((x + dir * radius).toFloat(), (top + bottom) / 2.0f, radius.toFloat(), p)
                }
                p.color = oldColor
                p.style = style
            }
        }
    }

    @SuppressLint("ParcelCreator")
    internal inner class CustomTypefaceSpan constructor(private val newType: Typeface) : TypefaceSpan("") {

        override fun updateDrawState(textPaint: TextPaint) {
            apply(textPaint, newType)
        }

        override fun updateMeasureState(paint: TextPaint) {
            apply(paint, newType)
        }

        private fun apply(paint: Paint, tf: Typeface) {
            val oldStyle: Int
            val old = paint.typeface
            if (old == null) {
                oldStyle = 0
            } else {
                oldStyle = old.style
            }

            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }

            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }

            paint.shader

            paint.typeface = tf
        }
    }

    internal inner class CustomImageSpan : CustomDynamicDrawableSpan {
        private var mDrawable: Drawable? = null
        private var mContentUri: Uri? = null
        private var mResourceId: Int = 0

        override val drawable: Drawable?
            get() {
                var drawable: Drawable? = null
                when {
                    mDrawable != null -> drawable = mDrawable
                    mContentUri != null -> {
                        val bitmap: Bitmap
                        try {
                            val inputStream = Utils.app.contentResolver.openInputStream(mContentUri)
                            bitmap = BitmapFactory.decodeStream(inputStream)
                            drawable = BitmapDrawable(Utils.app.resources, bitmap)
                            drawable.setBounds(
                                    0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight
                            )
                            inputStream?.close()
                        } catch (e: Exception) {
                            Log.e("sms", "Failed to loaded content " + mContentUri, e)
                        }

                    }
                    else -> try {
                        drawable = ContextCompat.getDrawable(Utils.app, mResourceId)
                        drawable!!.setBounds(
                                0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight
                        )
                    } catch (e: Exception) {
                        Log.e("sms", "Unable to find resource: " + mResourceId)
                    }
                }
                return drawable
            }

        constructor(b: Bitmap, verticalAlignment: Int) : super(verticalAlignment) {
            mDrawable = BitmapDrawable(Utils.app.resources, b)
            mDrawable!!.setBounds(
                    0, 0, mDrawable!!.intrinsicWidth, mDrawable!!.intrinsicHeight
            )
        }

        constructor(d: Drawable, verticalAlignment: Int) : super(verticalAlignment) {
            mDrawable = d
            mDrawable!!.setBounds(
                    0, 0, mDrawable!!.intrinsicWidth, mDrawable!!.intrinsicHeight
            )
        }

        constructor(uri: Uri, verticalAlignment: Int) : super(verticalAlignment) {
            mContentUri = uri
        }

        constructor(@DrawableRes resourceId: Int, verticalAlignment: Int) : super(verticalAlignment) {
            mResourceId = resourceId
        }
    }

    internal abstract inner class CustomDynamicDrawableSpan : ReplacementSpan {

        val mVerticalAlignment: Int

        abstract val drawable: Drawable?

        private val cachedDrawable: Drawable?
            get() {
                val wr = mDrawableRef
                var d: Drawable? = null
                if (wr != null) {
                    d = wr.get()
                }
                if (d == null && drawable != null) {
                    d = drawable
                    mDrawableRef = WeakReference(d!!)
                }
                return d
            }

        private var mDrawableRef: WeakReference<Drawable>? = null

        private constructor() {
            mVerticalAlignment = ALIGN_BOTTOM
        }

        constructor(verticalAlignment: Int) {
            mVerticalAlignment = verticalAlignment
        }

        override fun getSize(paint: Paint, text: CharSequence,
                             start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
            val d = cachedDrawable ?: return 0
            val rect = d.bounds
            if (fm != null) {
                //                LogUtils.d("fm.top: " + fm.top,
                //                        "fm.ascent: " + fm.ascent,
                //                        "fm.descent: " + fm.descent,
                //                        "fm.bottom: " + fm.bottom,
                //                        "lineHeight: " + (fm.bottom - fm.top));
                val lineHeight = fm.bottom - fm.top
                if (lineHeight < rect.height()) {
                    when (mVerticalAlignment) {
                        ALIGN_TOP -> {
                            fm.top = fm.top
                            fm.bottom = rect.height() + fm.top
                        }
                        ALIGN_CENTER -> {
                            fm.top = -rect.height() / 2 - lineHeight / 4
                            fm.bottom = rect.height() / 2 - lineHeight / 4
                        }
                        else -> {
                            fm.top = -rect.height() + fm.bottom
                            fm.bottom = fm.bottom
                        }
                    }
                    fm.ascent = fm.top
                    fm.descent = fm.bottom
                }
            }
            return rect.right
        }

        override fun draw(canvas: Canvas, text: CharSequence,
                          start: Int, end: Int, x: Float,
                          top: Int, y: Int, bottom: Int, paint: Paint) {
            val d = cachedDrawable
            if (d != null) {
                val rect = d.bounds
                canvas.save()
                val transY: Float
                val lineHeight = bottom - top
                //            LogUtils.d("rectHeight: " + rect.height(),
                //                    "lineHeight: " + (bottom - top));
                if (rect.height() < lineHeight) {
                    when (mVerticalAlignment) {
                        ALIGN_TOP -> transY = top.toFloat()
                        ALIGN_CENTER -> transY = ((bottom + top - rect.height()) / 2).toFloat()
                        ALIGN_BASELINE -> transY = (y - rect.height()).toFloat()
                        else -> transY = (bottom - rect.height()).toFloat()
                    }
                    canvas.translate(x, transY)
                } else {
                    canvas.translate(x, top.toFloat())
                }
                d.draw(canvas)
                canvas.restore()
            }
        }

    }

    internal inner class ShaderSpan constructor(private val mShader: Shader) : CharacterStyle(), UpdateAppearance {

        override fun updateDrawState(tp: TextPaint) {
            tp.shader = mShader
        }
    }

    internal inner class ShadowSpan constructor(private val radius: Float,
                                                private val dx: Float,
                                                private val dy: Float,
                                                private val shadowColor: Int) : CharacterStyle(), UpdateAppearance {

        override fun updateDrawState(tp: TextPaint) {
            tp.setShadowLayer(radius, dx, dy, shadowColor)
        }
    }

    companion object {
        private const val COLOR_DEFAULT = -0x1000001

        const val ALIGN_BOTTOM = 0
        const val ALIGN_BASELINE = 1
        const val ALIGN_CENTER = 2
        const val ALIGN_TOP = 3

        private val LINE_SEPARATOR = System.getProperty("line.separator")

        @IntDef(SpanUtils.ALIGN_BOTTOM.toLong(), SpanUtils.ALIGN_BASELINE.toLong(), SpanUtils.ALIGN_CENTER.toLong(), SpanUtils.ALIGN_TOP.toLong())
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        annotation class Align
    }
}
/**
 * 设置行高
 *
 * 当行高大于字体高度时，字体在行中的位置默认居中
 *
 * @param lineHeight 行高
 * @return [SpanUtils]
 */
/**
 * 设置引用线的颜色
 *
 * @param color 引用线的颜色
 * @return [SpanUtils]
 */
/**
 * 设置字体尺寸
 *
 * @param size 尺寸
 * @return [SpanUtils]
 */
/**
 * 追加图片
 *
 * @param bitmap 图片位图
 * @return [SpanUtils]
 */
/**
 * 追加图片
 *
 * @param drawable 图片资源
 * @return [SpanUtils]
 */
/**
 * 追加图片
 *
 * @param uri 图片 uri
 * @return [SpanUtils]
 */
/**
 * 追加图片
 *
 * @param resourceId 图片资源 id
 * @return [SpanUtils]
 */
/**
 * 追加空白
 *
 * @param size 间距
 * @return [SpanUtils]
 */
