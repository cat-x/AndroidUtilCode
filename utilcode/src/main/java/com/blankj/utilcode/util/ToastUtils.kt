package com.blankj.utilcode.util

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.view.ViewCompat
import android.support.v4.widget.TextViewCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/09/29
 * desc  : 吐司相关工具类
</pre> *
 */
class ToastUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object {

        private val COLOR_DEFAULT = -0x1000001
        private val HANDLER = Handler(Looper.getMainLooper())

        private var sToast: Toast? = null
        private var sGravity = -1
        private var sXOffset = -1
        private var sYOffset = -1
        private var sBgColor = COLOR_DEFAULT
        private var sBgResource = -1
        private var sMsgColor = COLOR_DEFAULT

        /**
         * 设置吐司位置
         *
         * @param gravity 位置
         * @param xOffset x 偏移
         * @param yOffset y 偏移
         */
        fun setGravity(gravity: Int, xOffset: Int, yOffset: Int) {
            sGravity = gravity
            sXOffset = xOffset
            sYOffset = yOffset
        }

        /**
         * 设置背景颜色
         *
         * @param backgroundColor 背景色
         */
        fun setBgColor(@ColorInt backgroundColor: Int) {
            sBgColor = backgroundColor
        }

        /**
         * 设置背景资源
         *
         * @param bgResource 背景资源
         */
        fun setBgResource(@DrawableRes bgResource: Int) {
            sBgResource = bgResource
        }

        /**
         * 设置消息颜色
         *
         * @param msgColor 颜色
         */
        fun setMsgColor(@ColorInt msgColor: Int) {
            sMsgColor = msgColor
        }

        /**
         * 安全地显示短时吐司
         *
         * @param text 文本
         */
        fun showShort(text: CharSequence) {
            show(text, Toast.LENGTH_SHORT)
        }

        /**
         * 安全地显示短时吐司
         *
         * @param resId 资源 Id
         */
        fun showShort(@StringRes resId: Int) {
            show(resId, Toast.LENGTH_SHORT)
        }

        /**
         * 安全地显示短时吐司
         *
         * @param resId 资源 Id
         * @param args  参数
         */
        fun showShort(@StringRes resId: Int, vararg args: Any) {
            if (args != null && args.size == 0) {
                show(resId, Toast.LENGTH_SHORT)
            } else {
                show(resId, Toast.LENGTH_SHORT, *args)
            }
        }

        /**
         * 安全地显示短时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showShort(format: String, vararg args: Any) {
            if (args != null && args.size == 0) {
                show(format, Toast.LENGTH_SHORT)
            } else {
                show(format, Toast.LENGTH_SHORT, *args)
            }
        }

        /**
         * 安全地显示长时吐司
         *
         * @param text 文本
         */
        fun showLong(text: CharSequence) {
            show(text, Toast.LENGTH_LONG)
        }

        /**
         * 安全地显示长时吐司
         *
         * @param resId 资源 Id
         */
        fun showLong(@StringRes resId: Int) {
            show(resId, Toast.LENGTH_LONG)
        }

        /**
         * 安全地显示长时吐司
         *
         * @param resId 资源 Id
         * @param args  参数
         */
        fun showLong(@StringRes resId: Int, vararg args: Any) {
            if (args != null && args.size == 0) {
                show(resId, Toast.LENGTH_SHORT)
            } else {
                show(resId, Toast.LENGTH_LONG, *args)
            }
        }

        /**
         * 安全地显示长时吐司
         *
         * @param format 格式
         * @param args   参数
         */
        fun showLong(format: String, vararg args: Any) {
            if (args != null && args.size == 0) {
                show(format, Toast.LENGTH_SHORT)
            } else {
                show(format, Toast.LENGTH_LONG, *args)
            }
        }

        /**
         * 安全地显示短时自定义吐司
         */
        fun showCustomShort(@LayoutRes layoutId: Int): View? {
            val view = getView(layoutId)
            show(view, Toast.LENGTH_SHORT)
            return view
        }

        /**
         * 安全地显示长时自定义吐司
         */
        fun showCustomLong(@LayoutRes layoutId: Int): View? {
            val view = getView(layoutId)
            show(view, Toast.LENGTH_LONG)
            return view
        }

        /**
         * 取消吐司显示
         */
        fun cancel() {
            if (sToast != null) {
                sToast!!.cancel()
                sToast = null
            }
        }

        private fun show(@StringRes resId: Int, duration: Int) {
            show(Utils.app.getResources().getText(resId).toString(), duration)
        }

        private fun show(@StringRes resId: Int, duration: Int, vararg args: Any) {
            show(String.format(Utils.app.getResources().getString(resId), *args), duration)
        }

        private fun show(format: String, duration: Int, vararg args: Any) {
            show(String.format(format, *args), duration)
        }

        private fun show(text: CharSequence, duration: Int) {
            HANDLER.post {
                cancel()
                sToast = Toast.makeText(Utils.app, text, duration)
                val tvMessage = sToast!!.view.findViewById<TextView>(android.R.id.message)
                val msgColor = tvMessage.currentTextColor
                //it solve the font of toast
                TextViewCompat.setTextAppearance(tvMessage, android.R.style.TextAppearance)
                if (sMsgColor != COLOR_DEFAULT) {
                    tvMessage.setTextColor(sMsgColor)
                } else {
                    tvMessage.setTextColor(msgColor)
                }
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    sToast!!.setGravity(sGravity, sXOffset, sYOffset)
                }
                setBg(tvMessage)
                sToast!!.show()
            }
        }

        private fun show(view: View?, duration: Int) {
            HANDLER.post {
                cancel()
                sToast = Toast(Utils.app)
                sToast!!.view = view
                sToast!!.duration = duration
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    sToast!!.setGravity(sGravity, sXOffset, sYOffset)
                }
                setBg()
                sToast!!.show()
            }
        }

        private fun setBg() {
            val toastView = sToast!!.view
            if (sBgResource != -1) {
                toastView.setBackgroundResource(sBgResource)
            } else if (sBgColor != COLOR_DEFAULT) {
                val background = toastView.background
                if (background != null) {
                    background.colorFilter = PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                } else {
                    ViewCompat.setBackground(toastView, ColorDrawable(sBgColor))
                }
            }
        }

        private fun setBg(tvMsg: TextView) {
            val toastView = sToast!!.view
            if (sBgResource != -1) {
                toastView.setBackgroundResource(sBgResource)
                tvMsg.setBackgroundColor(Color.TRANSPARENT)
            } else if (sBgColor != COLOR_DEFAULT) {
                val tvBg = toastView.background
                val msgBg = tvMsg.background
                if (tvBg != null && msgBg != null) {
                    tvBg.colorFilter = PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                    tvMsg.setBackgroundColor(Color.TRANSPARENT)
                } else if (tvBg != null) {
                    tvBg.colorFilter = PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                } else if (msgBg != null) {
                    msgBg.colorFilter = PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                } else {
                    toastView.setBackgroundColor(sBgColor)
                }
            }
        }

        private fun getView(@LayoutRes layoutId: Int): View? {
            val inflate = Utils.app.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as? LayoutInflater
            return inflate?.inflate(layoutId, null)
        }
    }
}
