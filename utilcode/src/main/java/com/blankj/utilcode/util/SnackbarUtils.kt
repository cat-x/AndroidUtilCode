package com.blankj.utilcode.util


import android.support.annotation.*
import android.support.annotation.IntRange
import android.support.design.widget.Snackbar
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.lang.ref.WeakReference

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/10/16
 * desc  : Snackbar 相关工具类
</pre> *
 */
class SnackbarUtils private constructor(private val parent: View?) {
    private var message: CharSequence? = null
    private var messageColor: Int = 0
    private var bgColor: Int = 0
    private var bgResource: Int = 0
    private var duration: Int = 0
    private var actionText: CharSequence? = null
    private var actionTextColor: Int = 0
    private var actionListener: View.OnClickListener? = null
    private var bottomMargin: Int = 0


    init {
        setDefault()
    }

    private fun setDefault() {
        message = ""
        messageColor = COLOR_DEFAULT
        bgColor = COLOR_DEFAULT
        bgResource = -1
        duration = LENGTH_SHORT.toInt()
        actionText = ""
        actionTextColor = COLOR_DEFAULT
        bottomMargin = 0
    }

    /**
     * 设置消息
     *
     * @param msg 消息
     * @return [SnackbarUtils]
     */
    fun setMessage(msg: CharSequence): SnackbarUtils {
        this.message = msg
        return this
    }

    /**
     * 设置消息颜色
     *
     * @param color 颜色
     * @return [SnackbarUtils]
     */
    fun setMessageColor(@ColorInt color: Int): SnackbarUtils {
        this.messageColor = color
        return this
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     * @return [SnackbarUtils]
     */
    fun setBgColor(@ColorInt color: Int): SnackbarUtils {
        this.bgColor = color
        return this
    }

    /**
     * 设置背景资源
     *
     * @param bgResource 背景资源
     * @return [SnackbarUtils]
     */
    fun setBgResource(@DrawableRes bgResource: Int): SnackbarUtils {
        this.bgResource = bgResource
        return this
    }

    /**
     * 设置显示时长
     *
     * @param duration 时长
     *
     *  * [Duration.LENGTH_INDEFINITE]永久
     *  * [Duration.LENGTH_SHORT]短时
     *  * [Duration.LENGTH_LONG]长时
     *
     * @return [SnackbarUtils]
     */
    fun setDuration(@Duration duration: Int): SnackbarUtils {
        this.duration = duration
        return this
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param listener 事件
     * @return [SnackbarUtils]
     */
    fun setAction(text: CharSequence,
                  listener: View.OnClickListener): SnackbarUtils {
        return setAction(text, COLOR_DEFAULT, listener)
    }

    /**
     * 设置行为
     *
     * @param text     文本
     * @param color    文本颜色
     * @param listener 事件
     * @return [SnackbarUtils]
     */

    fun setAction(text: CharSequence,
                  @ColorInt color: Int,
                  listener: View.OnClickListener): SnackbarUtils {
        this.actionText = text
        this.actionTextColor = color
        this.actionListener = listener
        return this
    }

    /**
     * 设置底边距
     *
     * @param bottomMargin 底边距
     */
    fun setBottomMargin(@IntRange(from = 1) bottomMargin: Int): SnackbarUtils {
        this.bottomMargin = bottomMargin
        return this
    }

    /**
     * 显示 snackbar
     */
    fun show() {
        val view = parent ?: return
        if (messageColor != COLOR_DEFAULT) {
            val spannableString = SpannableString(message)
            val colorSpan = ForegroundColorSpan(messageColor)
            spannableString.setSpan(
                    colorSpan, 0, spannableString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            sReference = WeakReference(Snackbar.make(view, spannableString, duration))
        } else {
            sReference = WeakReference(Snackbar.make(view, message!!, duration))
        }
        val snackbar = sReference!!.get()
        val snackbarView = snackbar?.getView()
        if (bgResource != -1) {
            snackbarView?.setBackgroundResource(bgResource)
        } else if (bgColor != COLOR_DEFAULT) {
            snackbarView?.setBackgroundColor(bgColor)
        }
        if (bottomMargin != 0) {
            val params = snackbarView?.layoutParams as ViewGroup.MarginLayoutParams
            params.bottomMargin = bottomMargin
        }
        if (actionText!!.length > 0 && actionListener != null) {
            if (actionTextColor != COLOR_DEFAULT) {
                snackbar?.setActionTextColor(actionTextColor)
            }
            snackbar?.setAction(actionText, actionListener)
        }
        snackbar?.show()
    }

    /**
     * 显示预设成功的 snackbar
     */
    fun showSuccess() {
        bgColor = COLOR_SUCCESS
        messageColor = COLOR_MESSAGE
        actionTextColor = COLOR_MESSAGE
        show()
    }

    /**
     * 显示预设警告的 snackbar
     */
    fun showWarning() {
        bgColor = COLOR_WARNING
        messageColor = COLOR_MESSAGE
        actionTextColor = COLOR_MESSAGE
        show()
    }

    /**
     * 显示预设错误的 snackbar
     */
    fun showError() {
        bgColor = COLOR_ERROR
        messageColor = COLOR_MESSAGE
        actionTextColor = COLOR_MESSAGE
        show()
    }

    companion object {
        const val LENGTH_INDEFINITE = -2
        const val LENGTH_SHORT = -1
        const val LENGTH_LONG = 0

        @IntDef(LENGTH_INDEFINITE.toLong(), LENGTH_SHORT.toLong(), LENGTH_LONG.toLong())
        @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
        annotation class Duration

        const private val COLOR_DEFAULT = -0x1000001
        const private val COLOR_SUCCESS = -0xd44a00
        const private val COLOR_WARNING = -0x3f00
        const private val COLOR_ERROR = -0x10000
        const private val COLOR_MESSAGE = -0x1

        private var sReference: WeakReference<Snackbar>? = null

        /**
         * 设置 snackbar 依赖 view
         *
         * @param parent 依赖 view
         * @return [SnackbarUtils]
         */
        fun with(parent: View): SnackbarUtils {
            return SnackbarUtils(parent)
        }

        /**
         * 消失 snackbar
         */
        fun dismiss() {
            if (sReference != null && sReference!!.get() != null) {
                sReference!!.get()?.dismiss()
                sReference = null
            }
        }

        /**
         * 获取 snackbar 视图
         *
         * @return snackbar 视图
         */
        val view: View?
            get() {
                val snackbar = sReference!!.get() ?: return null
                return snackbar.view
            }

        /**
         * 添加 snackbar 视图
         *
         * 在[.show]之后调用
         *
         * @param layoutId 布局文件
         * @param params   布局参数
         */
        fun addView(@LayoutRes layoutId: Int,
                    params: ViewGroup.LayoutParams) {
            val view = view
            if (view != null) {
                view.setPadding(0, 0, 0, 0)
                val layout = view as Snackbar.SnackbarLayout?
                val child = LayoutInflater.from(view.context).inflate(layoutId, null)
                layout!!.addView(child, -1, params)
            }
        }

        /**
         * 添加 snackbar 视图
         *
         * 在[.show]之后调用
         *
         * @param child  要添加的 view
         * @param params 布局参数
         */
        fun addView(child: View,
                    params: ViewGroup.LayoutParams) {
            val view = view
            if (view != null) {
                view.setPadding(0, 0, 0, 0)
                val layout = view as Snackbar.SnackbarLayout?
                layout!!.addView(child, params)
            }
        }
    }
}
